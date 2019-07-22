package com.spc.cdrm1;

import java.util.ArrayList;
import java.util.List;


public class DoSomething {
    private Buffer mBuf = new Buffer();

    public void produce() {
        synchronized (this) {
        	while (mBuf.isFull()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mBuf.add();
            notify();
        }
    }

    public void consume() {
        synchronized (this) {
            while (mBuf.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mBuf.remove();
            notify();
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
