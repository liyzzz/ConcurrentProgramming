package com.liyueze.security.val;

/**
 * @author Analyze
 * @create 2020-05-28 15:47
 **/
public class LazySimpleSingleton {
    private static volatile LazySimpleSingleton lazySimpleSingleton;

    private LazySimpleSingleton(){

    }

    public  static LazySimpleSingleton getInstance(){
        //业务代码
        /**
         * 采用双重if
         * 原因：
         * 如果去掉外面的if判断，就和上面的写法一样，这线程一进方法就被锁住，和在方法上加锁一样
         * 里面的if是为了当线程A进入外层if（lazySimpleSingleton =null）时，时间片耗尽。
         * 线程B发现lazySimpleSingleton还是null,一样进入if里。这时两个线程又会创建不同的对象了
         * 好处：
         * 当去掉外层的if时，相当于当一个线程进入方法后，其他线程都在阻塞状态，等着释放锁。
         * 而双重检验只有在lazySimpleSingleton ==null的时候才会出现阻塞，其他时候不会走synchronized块，提升效率
         */
        if(lazySimpleSingleton ==null){
            synchronized (LazySimpleSingleton.class){
                if(lazySimpleSingleton==null){
                    lazySimpleSingleton=new LazySimpleSingleton();
                }
            }
        }
        return lazySimpleSingleton;
    }
    //  防止序列化破坏单利
    private LazySimpleSingleton readResolve(){
        return lazySimpleSingleton;
    }

    public static void main(String[] args) {
        LazySimpleSingleton lazySimpleSingleton=LazySimpleSingleton.getInstance();
    }
}
