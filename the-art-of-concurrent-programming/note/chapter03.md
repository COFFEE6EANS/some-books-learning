# Java内存模型
- 内存模型概念
- 顺序一致性
- 同步原语
- 内存模型的设计
## 内存模型的基础
线程之间如何通信及线程之间如何同步？  
线程之间的通信机制有两种：共享内存和消息传递。
### 抽象结构
> 所有实例域、静态域和数组元素都存储在堆内存中中，堆内存在线程之间共享；   
        
主内存，本地内存（主内存的副本）
局部变量，方法定义参数和异常处理器参数不会在线程之间共享，
它们不会有内存可见性问题，也不受内存模型的影响。

JMM通过控制主内存与每个线程的本地内存之间的交互，来为Java程序员提供内存可见性保证.