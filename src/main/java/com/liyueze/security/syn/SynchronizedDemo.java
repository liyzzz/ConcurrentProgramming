package com.liyueze.security.syn;

/**
 * Synchronized的使用方式可以分为两大类。每个大类分为两个小类
 * 1.加在方法上：
 * 1.1 加在静态方法上（其作用的范围是整个静态方法，作用的对象是这个类的所有对象）
 * 1.2 加在实例方法上（其作用的范围是整个静态方法，作用的对象是这个类的所有对象）
 * 2.静态块:
 * 2.1 对给定对象代码块加锁（其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象）
 * 2.2 对给定类加锁（其作用的范围是synchronized后面括号括起来的部分，作用的对象是这个类的所有对象）
 *  注：作用范围时候指什么时候加锁，作用的对象是指锁住的范围
 *  注：作用范围越小性能越好，锁住范围约少性能越好
 *  注：类在方法区，所有对象公用一份，对象在堆中，一个对象一块区域
 *  注: 加锁锁的不是代码,而是对象（锁信息在对象头里）！！
 */
public class SynchronizedDemo {
    //在静态方法上加锁
    public synchronized static void addStaticMethod(){
        System.out.println(" add Static Method");
    }

    //在实例方法上加锁
    public synchronized  void addMethod(){
        System.out.println("add Method");
    }

    //在对象上代码块加锁
    public void addObject(){
        synchronized (this){
            System.out.println("add Object");
        }
    }

    //在类上代码块加锁
    public void addClass(){
        synchronized (this.getClass()){
            System.out.println("add Class");
        }
    }


    public static void NoStaticLock(){
        System.out.println("No Static Lock");
    }

    public  void NoLock(){
        System.out.println("No Lock");
    }

}
