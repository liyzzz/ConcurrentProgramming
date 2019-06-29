package com.liyueze.base;

import java.util.concurrent.TimeUnit;

/**
 * InterruptedException意味着：唤醒阻塞线程并将Interrupted复位(false),线程恢复正常运行
 */
public class InterruptedExceptionDemo {
    private static int i;

    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                System.out.println("线程正在进行");
                System.out.println("Interrupted:"+Thread.currentThread().isInterrupted());
                try {
                    //中断一个处于阻塞状态的线程会抛出异常。join/wait/queue.take..
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("demo");
                } catch (InterruptedException e) {
                    System.out.println("抛出异常");
                    //抛出异常后将interrupted复位,线程还在运行
                    System.out.println("Interrupted:"+Thread.currentThread().isInterrupted());
                    e.printStackTrace();
//                    break;//会跳出当前循环，使得线程结束（run方法执行完毕）
                }
            }
            System.out.println("i:"+i);
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        //设置interrupted()为true
        thread.interrupt();
        System.out.println(thread.isInterrupted());
    }
}
