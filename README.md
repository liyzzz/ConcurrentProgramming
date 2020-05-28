# ConcurrentProgramming
并发编程
## 多线程基础
多线程基础示例都在base包下
### 多线程有什么好处
为了使CPU资源充分利用，当某个作业因为I/O而阻塞时，CPU可以执其他作业提高效率
### 异步,同步，并行，并发，串行
**同步**：多个任务情况下，一个任务A执行结束，才可以执行另一个任务B。只存在一个线程。  
**异步**：多个任务情况下，一个任务A正在执行，同时可以执行另一个任务B。任务B不用等待任务A结束才执行。存在多条线程。   
而**并发**和**并行**其实是**异步线程**实现的两种形式，**串行**是**同步线程**的实现方式  
**并行**（多核CPU,且cpu分配线程在不同核执行）：真正意义上的同时，执行A任务的同时执行多任务  
**并发**（单核CPU,或CPU分配线程在同一核执行）：从微观层面上看，并发是在一条CPU上不停的切换任务。比如任务A执行了20%，任务A停下里，线程让给任务B，任务执行了30%停下，再让任务A执行。这样我们用的时候，由于CUP处理速度快，
所以从宏观上看起来好像是同时执行，其实并不是的，同一时间只会执行单个任务  
**串行**：任务有顺序的执行，必须等前一个任务执行完才能执行下一个任务
### 线程和进程区别
系统会按进程来分配资源；线程是CPU调度的基本单位不可独立执行；  
进程可以拥有系统资源；而线程基本上不拥有系统资源,只拥有一点在运行中必不可少的资源(如程序计数器,一组寄存器和栈),
但是它可与同属一个进程的其他的线程共享进程所拥有的全部资源；

从逻辑角度来看，多线程的意义在于一个应用程序中，有多个执行部分可以同时执行。但操作系统并没有将多个线程看做多个独立的应用，来实现进程的调度和管理以及资源分配
### java内存模型和线程的关系
堆在多线程中是共享的，而栈是每个线程独享的
### 线程的实现方式
1.实现Runnable接口（示例见：MyRunnable）   
2.继承Tread类（示例见：MyThread）  
3.实现 Callable接口（示例见：MyCallable）
### 线程的六种状态
博文[Java线程的6种状态及切换(透彻讲解)](https://blog.csdn.net/pange1991/article/details/53860651) 总结很好，不再赘述   
示例代码见ThreadStatusDemo类
### 如何查看某个线程栈日志：
```
找见class文件，在该目录下运行cmd  
输入命令：jsp              查看所有运行的java线程和线程的Id  
再输入:jstack 线程ID       查看该线程的栈日志 
```
### 如何查看native方法的源码
以openJDK为例，openJDK采用hotspot虚拟机  
http://hg.openjdk.java.net/  可以查看
### run方法和start方法区别
start方法是一个信号，告诉cpu我准好了，可以执行了；  
而run方法是线程具体需要的执行的逻辑   
start方法最终回调了run方法 
### InterruptedException
这个异常的意思是表示**一个阻塞被其他线程中断**了。  

为什么 Object.wait、Thread.sleep 和 Thread.join都会抛出InterruptedException? 
  
这几个方法有一个共同点，都是属于阻塞的方法，  
而阻塞方法的释放会取决于一些外部的事件,但是阻塞方法可能因为等不到外部的触发事件而导致一直阻塞，所以它允许一个线程请求自己来停止它正在做的事情。  
当一个方法抛出 InterruptedException 时，它是在告诉调用者如果执行该方法的线程被中断， 
它会尝试停止正在做的事情并且通过抛出 InterruptedException 表示提前返回。    
由于线程调用了 interrupt()中断方法，那么Object.wait、Thread.sleep 等被**阻塞的线程被唤醒**以后会通过is_interrupted 方法判断中断标识的状态变化，  
如果发现中断标识为 true，则**先清除中断标识**(设置为false)，然后抛出InterruptedException，**线程继续运行**     
具体的示例代码见InterruptedExceptionDemo
### 线程安全
#### 什么是线程安全
一个对象是否是线程安全的，取决于它是否会被多个线程
访问，以及程序中是如何去使用这个对象的。如果
多个线程访问同一个共享对象，在不需额外的同步以及调
用端代码不用做其他协调的情况下，这个共享对象的状态
依然是正确的（正确性意味着这个对象的结果与我们预期
规定的结果保持一致），那说明这个对象是线程安全的。  


解决线程安全的方式通常是加锁
#### java中锁分类
美团有一篇技术博客总结的非常好：[不可不说的Java“锁”事](https://tech.meituan.com/2018/11/15/java-lock.html)  
这里不再赘述
### 什么CAS
参见博文[面试必问的CAS，你懂了吗？](https://zhuanlan.zhihu.com/p/34556594)  
CAS的全程是 compare and swap 从名字就可以看出来，他做了比较并替换  
该算法有三个参数：1、内存值V；2.旧的预期值A 3.即将更新的值B  
当且仅当内存值V的值等于旧的预期值A时才会将内存值V的值修改为B，否则什么都不干。
其伪代码如下：
```
if(this.value == A){
    this.value = B
    return true;
}else{
    return false;
}
```
因为经常配合循环操作，直到完成为止，所以泛指一类操作(自旋 / 自旋锁 / 无锁 （无重量锁）)  

cas在jdk有着大量的应用,unsafe类的compareAndSwapInt方法就是jdk提供的cas实现(见包com.liyueze.security.cas)  
  
compareAndSwapInt方法在hotspot中实现原理：  
jdk8u: unsafe.cpp:

cmpxchg = compare and exchange

```c++
UNSAFE_ENTRY(jboolean, Unsafe_CompareAndSwapInt(JNIEnv *env, jobject unsafe, jobject obj, jlong offset, jint e, jint x))
  UnsafeWrapper("Unsafe_CompareAndSwapInt");
  oop p = JNIHandles::resolve(obj);
  jint* addr = (jint *) index_oop_from_field_offset_long(p, offset);
  return (jint)(Atomic::cmpxchg(x, addr, e)) == e;
UNSAFE_END
```

jdk8u: atomic_linux_x86.inline.hpp **93行**

is_MP = Multi Processor  

```c++
inline jint     Atomic::cmpxchg    (jint     exchange_value, volatile jint*     dest, jint     compare_value) {
  int mp = os::is_MP();
  __asm__ volatile (LOCK_IF_MP(%4) "cmpxchgl %1,(%3)"
                    : "=a" (exchange_value)
                    : "r" (exchange_value), "a" (compare_value), "r" (dest), "r" (mp)
                    : "cc", "memory");
  return exchange_value;
}
```

jdk8u: os.hpp is_MP()

```c++
  static inline bool is_MP() {
    // During bootstrap if _processor_count is not yet initialized
    // we claim to be MP as that is safest. If any platform has a
    // stub generator that might be triggered in this phase and for
    // which being declared MP when in fact not, is a problem - then
    // the bootstrap routine for the stub generator needs to check
    // the processor count directly and leave the bootstrap routine
    // in place until called after initialization has ocurred.
    return (_processor_count != 1) || AssumeMP;
  }
```

jdk8u: atomic_linux_x86.inline.hpp

```c++
#define LOCK_IF_MP(mp) "cmp $0, " #mp "; je 1f; lock; 1: "
```

最终实现：

cmpxchg = cas修改变量值

```assembly
lock cmpxchg 指令
```

硬件：

lock指令在执行后面指令的时候锁定一个北桥信号

（不采用锁总线的方式）


CAS的问题：  
1.循环时间长开销很大  
2.只能针对一个共享变量   
3.ABA 问题（如果一个值原来是A，变成了B，然后又变成了A，那么在CAS检查的时候会发现没有改变就会做替换操作，但是实质上它已经发生了改变）
### Synchronized
Synchronized的使用方式参见SynchronizedDemo
关于Synchronized基础准备知识参考博文[java 中的锁 -- 偏向锁、轻量级锁、自旋锁、重量级锁](https://blog.csdn.net/zqz_zqz/article/details/70233767)  
注： 
1. 该博文中将自旋锁和其他三种锁并列，这种分类有误，详情参考上面美团的锁分类。**自旋锁只是轻量级锁的实现方式**  

JDK较早的版本(重量级锁) OS的资源 互斥量 用户态 -> 内核态的转换 重量级 效率比较低

现代版本进行了优化

无锁 - 偏向锁 -轻量级锁（自旋锁）-重量级锁

java对象头 :  
jdk8u: markOop.hpp

```java
// Bit-format of an object header (most significant first, big endian layout below):
//
//  32 bits:
//  --------
//             hash:25 ------------>| age:4    biased_lock:1 lock:2 (normal object)
//             JavaThread*:23 epoch:2 age:4    biased_lock:1 lock:2 (biased object)
//             size:32 ------------------------------------------>| (CMS free block)
//             PromotedObject*:29 ---------->| promo_bits:3 ----->| (CMS promoted object)
//
//  64 bits:
//  --------
//  unused:25 hash:31 -->| unused:1   age:4    biased_lock:1 lock:2 (normal object)
//  JavaThread*:54 epoch:2 unused:1   age:4    biased_lock:1 lock:2 (biased object)
//  PromotedObject*:61 --------------------->| promo_bits:3 ----->| (CMS promoted object)
//  size:64 ----------------------------------------------------->| (CMS free block)
//
//  unused:25 hash:31 -->| cms_free:1 age:4    biased_lock:1 lock:2 (COOPs && normal object)
//  JavaThread*:54 epoch:2 cms_free:1 age:4    biased_lock:1 lock:2 (COOPs && biased object)
//  narrowOop:32 unused:24 cms_free:1 unused:4 promo_bits:3 ----->| (COOPs && CMS promoted object)
//  unused:21 size:35 -->| cms_free:1 unused:7 ------------------>| (COOPs && CMS free block)
```
 ![image](https://github.com/liyzzz/ConcurrentProgramming/blob/master/image/markword-64.png)<br>
 
 示例代码见  JavaLayout
 
 #### 锁升级过程
 无锁 - 偏向锁 - 轻量级锁 （自旋锁，自适应自旋）- 重量级锁
 ![image](https://github.com/liyzzz/ConcurrentProgramming/blob/master/image/lock_step.png)<br>


偏向锁 - markword 上记录当前线程指针，下次同一个线程加锁的时候，不需要争用，只需要判断线程指针是否同一个，所以，偏向锁，偏向加锁的第一个线程 。hashCode备份在线程栈上 线程销毁，锁降级为无锁

有争用 - 锁升级为轻量级锁 - 每个线程有自己的LockRecord在自己的线程栈上，用CAS去争用markword的LR的指针，指针指向哪个线程的LR，哪个线程就拥有锁

自旋超过10次，升级为重量级锁 - 如果太多线程自旋 CPU消耗过大，不如升级为重量级锁，进入等待队列（不消耗CPU）-XX:PreBlockSpin

自旋锁在 JDK1.4.2 中引入，使用 -XX:+UseSpinning 来开启。JDK 6 中变为默认开启，并且引入了自适应的自旋锁（适应性自旋锁）。

自适应自旋锁意味着自旋的时间（次数）不再固定，而是由前一次在同一个锁上的自旋时间及锁的拥有者的状态来决定。如果在同一个锁对象上，自旋等待刚刚成功获得过锁，并且持有锁的线程正在运行中，那么虚拟机就会认为这次自旋也是很有可能再次成功，进而它将允许自旋等待持续相对更长的时间。如果对于某个锁，自旋很少成功获得过，那在以后尝试获取这个锁时将可能省略掉自旋过程，直接阻塞线程，避免浪费处理器资源。

偏向锁由于有锁撤销的过程revoke，会消耗系统资源，所以，在锁争用特别激烈的时候，用偏向锁未必效率高。还不如直接使用轻量级锁。

#### 锁消除 lock eliminate

```java
public void add(String str1,String str2){
         StringBuffer sb = new StringBuffer();
         sb.append(str1).append(str2);
}
```

我们都知道 StringBuffer 是线程安全的，因为它的关键方法都是被 synchronized 修饰过的，但我们看上面这段代码，我们会发现，sb 这个引用只会在 add 方法中使用，不可能被其它线程引用（因为是局部变量，栈私有），因此 sb 是不可能共享的资源，JVM 会自动消除 StringBuffer 对象内部的锁。

### 锁粗化 lock coarsening

```java
public String test(String str){
       
       int i = 0;
       StringBuffer sb = new StringBuffer():
       while(i < 100){
           sb.append(str);
           i++;
       }
       return sb.toString():
}
```

JVM 会检测到这样一连串的操作都对同一个对象加锁（while 循环内 100 次执行 append，没有锁粗化的就要进行 100  次加锁/解锁），此时 JVM 就会将加锁的范围粗化到这一连串的操作的外部（比如 while 虚幻体外），使得这一连串操作只需要加一次锁即可。

### 锁降级

https://www.zhihu.com/question/63859501  
降级对象就是那些仅仅能被VMThread(对象监视器——Object Monitor)访问而没有其他JavaThread访问的Monitor对象。
其实，只被VMThread访问，降级也就没啥意义了。所以可以简单认为锁降级不存在！


## volatile
#### 1.线程可见性
见 VolatileDemo

原理：  
补充计算机组成原理知识 见
![image](https://github.com/liyzzz/ConcurrentProgramming/blob/master/image/one-cpu.png)<br>
![image](https://github.com/liyzzz/ConcurrentProgramming/blob/master/image/more-cpu.png)<br>

cpu读主内存数据是以一个缓存行为单位（大小为64byte),如果一个缓存行发生变回就会通知其他线程回主内存重新读取

如果数据大于一个缓存行就加总线锁（lock）

缓存行示例见CacheLine(伪共享)和CacheLinePadding
结果说明:缓存行对齐时，线程持有数组的一个对象不用通知其他cpu所以速度快,而缓存行没有对齐时，需要通知其他cpu所以慢

详细见博客
https://www.jianshu.com/p/7f89650367b8

很多博客内容将MESI和volatile搞在一起,但是其实他们中间差了很多的抽象  
见知乎:https://www.zhihu.com/question/296949412

#### 2.指令重排序
乱序执行:https://blog.csdn.net/dd864140130/article/details/56494925  
证明乱序执行:Disorder
乱序执行会产生的问题:  
DoubleCheck单例需要用volatile修饰嘛?  需要  
原因是 new 字节码：
```
       //在堆中创建对象并赋初始值
       17 new #3 <com/liyueze/security/val/LazySimpleSingleton>  
       20 dup
       //调用构造方法
       21 invokespecial #4 <com/liyueze/security/val/LazySimpleSingleton.<init>>
       24 putstatic #2 <com/liyueze/security/val/LazySimpleSingleton.lazySimpleSingleton>
       //指向创建的对象
       27 aload_0
```
21和27有可能会调换顺序(指令重排序) 从而导致成员变量都是初始值，而不是构造方法中的初始值
#### volatile实现
当用volatile修饰的时字节码会加一个修饰符 ACC_VOLATILE  
hostspot的实现:
```c++
int field_offset = cache->f2_as_index();
          if (cache->is_volatile()) {
            if (support_IRIW_for_not_multiple_copy_atomic_cpu) {
              OrderAccess::fence();
            }
```

orderaccess_linux_x86.inline.hpp

```c++
inline void OrderAccess::fence() {
  if (os::is_MP()) {
    // always use locked addl since mfence is sometimes expensive
#ifdef AMD64
    __asm__ volatile ("lock; addl $0,0(%%rsp)" : : : "cc", "memory");
#else
    __asm__ volatile ("lock; addl $0,0(%%esp)" : : : "cc", "memory");
#endif
  }
}
```


