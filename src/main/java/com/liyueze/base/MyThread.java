package com.liyueze.base;

/**
 * 继承Tread实现线程
 * Tread实现了Runnable接口
 */
public class MyThread  extends Thread{

    @Override
    public void run() {
        this.setName("MyThread");
        System.out.println("MyThread正在运行");
        super.run();
    }

    public static void main(String[] args) {
        //两种方式同样的效果
        new MyThread().start();
        //Thread中构造方法会将传入的值赋值给target，调用start其实就是在调用target.start()
        new Thread(new MyThread()).start();
    }
}
