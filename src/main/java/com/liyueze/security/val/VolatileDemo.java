package com.liyueze.security.val;

/**
 * 该示例的目的是让线程做累加
 * count累加1000次应该说结果是1000
 * 可实际的结果总是小于1000
 *  这就是线程不安全
 *  原因是count++不是原子操作，它分为三步：1.取出count的值。2.将count的值加1 3.将累加后的值赋值给count
 *  如果一个线程1取出count的值为7然后加1得到8，这时cpu时间耗尽切换到另一个线程
 *  另外一个线程2取出count的值为7（因为上一个线程还没有赋值）,然后加1得到8并把值赋值给了count
 *  然后轮到线程1，线程1只剩下赋值操作，就把8计算的值赋值给了count.
 *  两个线程应该最后得到的结果是9，可是最后的结果却变成了8
 */
public class VolatileDemo {
    private /*volatile*/ static int count = 0;

    public static void inc() {
        count++;
    }

    public static void main(String[] args)
            throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> VolatileDemo.inc()).start();
        }
        Thread.sleep(3000);
        System.out.println(" 运行结果" + count);
    }
}
