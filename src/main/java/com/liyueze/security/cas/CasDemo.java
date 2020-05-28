package com.liyueze.security.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Analyze
 * @create 2020-05-28 11:30
 **/
public class CasDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger=new AtomicInteger(1);
        atomicInteger.incrementAndGet();
        /**
         * 该方法会调用 Unsafe类的
         * public final int getAndAddInt(Object var1, long var2, int var4) {
         *         int var5;
         *         //这里是自旋
         *         do {
         *             var5 = this.getIntVolatile(var1, var2);
         *             //这里就是cas
         *         } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
         *
         *         return var5;
         *     }
         */
    }
}
