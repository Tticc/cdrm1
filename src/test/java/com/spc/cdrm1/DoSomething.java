package com.spc.cdrm1;

import java.util.ArrayList;
import java.util.List;


/**
 * 测试生产者消费者的过度生产、消费，死锁
 * @author cv
 * Aug 6, 2019
 */
public class DoSomething {
    private Buffer mBuf = new Buffer();

    public void produce() {
        synchronized (this) {
        	// while，而不是if，如果使用if，那么可能在mBuf为full的情况下执行add，造成过度生产
        	while (mBuf.isFull()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mBuf.add();
            // 使用notifyAll而不是notify，使用notify在多个生产者的情况下可能造成死锁
            //notify();
            notifyAll();
        }
    }

    public void consume() {
        synchronized (this) {
        	// while，而不是if，如果使用if，那么可能在mBuf为空的情况下执行remove，造成过度消费
            while (mBuf.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mBuf.remove();
            // 使用notifyAll而不是notify，使用notify在多个消费者的情况下可能造成死锁
            //notify();
            notifyAll();
        }
    }

    private class Buffer {
        private static final int MAX_CAPACITY = 1;
        private List innerList = new ArrayList<>(MAX_CAPACITY);

        void add() {
            if (isFull()) {
                throw new IndexOutOfBoundsException();
            } else {
                innerList.add(new Object());
            }
            System.out.println(Thread.currentThread().toString() + " add");

        }

        void remove() {
            if (isEmpty()) {
                throw new IndexOutOfBoundsException();
            } else {
                innerList.remove(innerList.size() - 1);
            }
            System.out.println(Thread.currentThread().toString() + " remove");
        }

        boolean isEmpty() {
            return innerList.isEmpty();
        }

        boolean isFull() {
            return innerList.size() == MAX_CAPACITY;
        }
    }

    public static void main(String[] args) {
        DoSomething sth = new DoSomething();
        Runnable runProduce = new Runnable() {
            int count = 8;

            @Override
            public void run() {
                while (count-- > 0) {
                    sth.produce();
                }
            }
        };
        Runnable runConsume = new Runnable() {
            int count = 8;

            @Override
            public void run() {
                while (count-- > 0) {
                    sth.consume();
                }
            }
        };
        for (int i = 0; i < 2; i++) {
            new Thread(runConsume).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(runProduce).start();
        }
    }
}
