package com.liyueze.base;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 有返回值的线程
 */
public class MyCallable implements Callable<String> {
    private int id;

    public MyCallable(int id) {
        this.id = id;
    }

    //相当于runnable中的run方法吗，只是Callable可以有返回值且可以抛出异常，而run不能
    @Override
    public String call() throws Exception {
        return "result :" + id;
    }

    public static void main(String[] args) {

        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            //Future 相当于是用来存放Executor执行的结果的一种容器
            ArrayList<Future<String>> results = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                results.add(exec.submit(new MyCallable(i)));
            }
            for (Future<String> fs : results) {
                if (fs.isDone()) {
                    //get方法获取执行结果,这个方法会产生阻塞，会一直等到任务执行完毕才返回
                    System.out.println(fs.get());

                } else {
                    System.out.println("Future result is not yet complete");
                }
                exec.shutdown();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
