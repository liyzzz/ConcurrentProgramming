package com.liyueze.security.syn;

import lombok.SneakyThrows;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author Analyze
 * @create 2020-05-28 11:51
 **/
public class JavaLayout {
    @SneakyThrows
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        /**
         * 调用hashCode方法才会在对象头中写入hash值
         *
         * 分代年龄:GC每次回收会+1 默认是大于15为老年代  网上有些GC调优说可以将这个值调大（至越大老年代的越少），但是这个错误的，应为分代年龄只有4个byte,最大为15
         */
//        o.hashCode();
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());

       /* synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }*/
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
