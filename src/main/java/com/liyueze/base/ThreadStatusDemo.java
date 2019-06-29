package com.liyueze.base;

import java.util.concurrent.TimeUnit;

/**
 * 运行状态无示例代码（Thead.start()等待cpu分配时间片，有时间片了自然就开始running状态）
 */
public class ThreadStatusDemo {

    /**
     * running状态到timeWaiting状态
     * timeWaiting是有时限的waiting状态，唤醒的方式通过时间，时间到了就运行
     * sleep方法，wait方法和join方法都可以设置时效
     */
    public void timeWaiting() {
        MyThread myThread = new MyThread();
        new Thread(() -> {
            try {
                //sleep:
                TimeUnit.SECONDS.sleep(10);//单位为秒
                Thread.sleep(100);//单位为毫秒
                //wait:
                wait(100);//单位为毫秒
                TimeUnit.SECONDS.wait(10);//单位为秒
//              join:
                myThread.join(100);//单位为毫秒
                TimeUnit.SECONDS.timedJoin(myThread,10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "timeWaiting").start();

    }


    /**
     * running状态到waiting状态
     * 唤醒的方式不能设置时间
     * sleep方法必须设置时间，所以sleep方法不能让线程到waiting状态
     * 只有wait和join两个方法
     * wait可以通过notify方法或notifyAll方法唤醒
     * join是变成串行，线程执行完毕就唤醒了
     */
    public void waiting() {
        MyThread myThread = new MyThread();
        new Thread(() -> {
            try {
                //wait:
                wait();
//              join:
                myThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "waiting").start();
    }

    /**
     * running状态到blocked状态
     */
    public static void blocked(){
        new Thread(new BlockedDemo(),"Blocked01_Thread").start();
        new Thread(new BlockedDemo(),"Blocked02_Thread").start();
    }

    private static class BlockedDemo extends Thread{
        @Override
        public void run() {
            while (true){

            }
        }
    }


    /**
     * running到终止状态的三种方式：
     * 1.run方法执行完
     * 2.（已废弃）在其他线程里直接结束，例如stop，suspend方法
     * 3.由其他线程告知该线程结束（还是执行完run方法结束）
     * 注意：抛异常线程并不会结束
     */
    public static void terminated() throws InterruptedException {
        Thread thread=new Thread(()->{
            while(true){
                if(Thread.currentThread().isInterrupted()){
                    break;
                }
                System.out.println("线程正在执行");
            }
            System.out.println("isInterrupted标识已经变为true,可以结束了");
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        /*
        * stop方法和suspend方法都已经被废弃
        * 原因是
        * 由其他线程直接结束,太粗暴不安全，例如一些已经开启的资源未被清理。
        * 这就好比你在厕所拉屎，刚拉了一半，另外一个人不管三七二十暴力的把你拉出来
        * */
//        thread.stop();
//        thread.suspend();
        /*
        * 优雅的方式应该使用interrupt方法，将interrupted标识设置为true
        * interrupted标识默认为false状态，既非中断状态
        * 通过标识位或者中断操作的方式能够使线程在终止时有机会去清理资源，而不是武断地将线程停止，
        * 因此这种终止线程的做法显得更加安全和优雅
        * 这样就好比是你在厕所拉屎，刚拉了一半，另外一个人很与礼节的敲了敲门，告诉你快点出来吧。
        * 至于什么时候你停止拉屎取决的自己
        * */
        thread.interrupt(); //把isInterrupted设置成true
    }


    /**
     * interrupted标识复位
     * 使用interrupted()可以使得当前线程interrupted标识复位为false
     * 该示例方法一致不会结束
     */
    public static void interrupted() throws InterruptedException{
        Thread thread=new Thread(()->{
            while(true){
                System.out.println("线程正在执行");
                if(Thread.currentThread().isInterrupted()){//主线程睡眠1S后，isInterrupted标识为true
                    System.out.println("before:"+Thread.currentThread().isInterrupted());
                    //复位，interrupted回到初始状态false
                    Thread.interrupted();
                    System.out.println("after:"+Thread.currentThread().isInterrupted());
                }
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt(); //把isInterrupted设置成true
    }


    public static void main(String[] args) {
        try {
            interrupted();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
