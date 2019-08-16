package com.spc.other.rpcAnetty;

import java.lang.reflect.Field;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;



/**
 * 模仿futuretask，用来在netty channel外获取channel的返回值
 * @author cv
 * Aug 13, 2019
 */
public class MyFutureTask<V> {

	private FutureTask<V> f;
    private volatile int state;
    private static final int NEW          = 0;
    private static final int COMPLETING   = 1;
    private static final int NORMAL       = 2;
    private static final int EXCEPTIONAL  = 3;
    private static final int CANCELLED    = 4;
    private static final int INTERRUPTING = 5;
    private static final int INTERRUPTED  = 6;
    /** The result to return or exception to throw from get() */
    private Object outcome; // non-volatile, protected by state reads/writes
    
    // 保存所有正在等待 get() 返回值的线程。
    /** Treiber stack of waiting threads */
    private volatile WaitNode waiters;
    
    //暂时没有作用
    /** The thread running the callable; CASed during run() */
    private volatile Thread runner;
    
    public MyFutureTask() {
        this.state = NEW;       // ensure visibility of callable
    }
    
    /**
     * 设置返回值，并调用 LockSupport.unpark() 唤醒所有因为调用 get() 而阻塞着的线程
     * @author Wen, Changying
     * @param v
     * @date 2019年8月15日
     */
    public void set(V v) {
//    	try {
//			Thread.sleep(8000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
    	this.outcome = v;
    	this.state = NORMAL;
    	while(waiters !=null) {
    		LockSupport.unpark(waiters.thread);
    		waiters = waiters.next;
    	}
    }
    /**
     * FutureTask原有方法
     * @throws CancellationException {@inheritDoc}
     */
    public V get() throws InterruptedException, ExecutionException {
        int s = state;
        if (s <= COMPLETING)
            s = awaitDone(false, 0L);
        return report(s);
    }

    /**
     * FutureTask原有方法。我注释掉了两行 removeWaiter(q);</br>
     * Awaits completion or aborts on interrupt or timeout.
     *
     * @param timed true if use timed waits
     * @param nanos time to wait, if timed
     * @return state upon completion
     */
    private int awaitDone(boolean timed, long nanos)
        throws InterruptedException {
        final long deadline = timed ? System.nanoTime() + nanos : 0L;
        WaitNode q = null;
        boolean queued = false;
        for (;;) {
            if (Thread.interrupted()) {
//                removeWaiter(q);
                throw new InterruptedException();
            }

            int s = state;
            if (s > COMPLETING) {
                if (q != null)
                    q.thread = null;
                return s;
            }
            else if (s == COMPLETING) // cannot time out yet
                Thread.yield();
            else if (q == null)
                q = new WaitNode();
            else if (!queued) {
                queued = UNSAFE.compareAndSwapObject(this, waitersOffset,
                                                     q.next = waiters, q);
                waiters = q;
            }
            else if (timed) {
                nanos = deadline - System.nanoTime();
                if (nanos <= 0L) {
//                    removeWaiter(q);
                    return state;
                }
                LockSupport.parkNanos(this, nanos);
            }
            else
                LockSupport.park(this);
        }
    }
    /**
     * FutureTask原有方法</br>
     * Returns result or throws exception for completed task.
     *
     * @param s completed state value
     */
    @SuppressWarnings("unchecked")
    private V report(int s) throws ExecutionException {
        Object x = outcome;
        if (s == NORMAL)
            return (V)x;
        if (s >= CANCELLED)
            throw new CancellationException();
        throw new ExecutionException((Throwable)x);
    }

    /**
     * FutureTask原有内部类
     * @author Wen, Changying
     * 2019年8月15日
     */
    static final class WaitNode {
        volatile Thread thread;
        volatile WaitNode next;
        WaitNode() { thread = Thread.currentThread(); }
    }
    // Unsafe mechanics
    /**
     * Unsafe机制
     */
    private static final sun.misc.Unsafe UNSAFE;
    private static final long stateOffset;
    private static final long runnerOffset;
    private static final long waitersOffset;
    static {
        try {
        	//FutureTask原有方法，jdk1.9之前不可用
            //UNSAFE = sun.misc.Unsafe.getUnsafe();
        	
        	//使用反射的方式，强行使用Unsafe API。在jdk1.9之后开源了
        	Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        	f.setAccessible(true);
        	UNSAFE = (sun.misc.Unsafe) f.get(null);
        	
        	
            Class<?> k = MyFutureTask.class;
            stateOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("state"));
            runnerOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("runner"));
            waitersOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("waiters"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
    
}
