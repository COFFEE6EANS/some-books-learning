## Executor框架
Java线程的创建与销毁需要一定的开销，如果我们为每一个任务创建一个新线程来执行，  
这些线程的创建与销毁将消耗大量的计算资源。同时，为每一个任务创建一个新线程来执行，  
这种策略可能会使处于高负荷状态的应用最终崩溃。

从JDK 5开始，把工作单元与执行机制分离开 来。工作单元包括Runnable和Callable，   
而执行机制由Executor框架提供。

### Executor框架的结构
Executor框架主要由3大部分组成如下。  
- 任务。包括被执行任务需要实现的接口：Runnable接口或Callable接口。  
- 任务的执行。包括任务执行机制的核心接口Executor，以及继承自Executor的 ExecutorService接口。
Executor框架有两个关键类实现了ExecutorService接口 （ThreadPoolExecutor和ScheduledThreadPoolExecutor）。
- 异步计算的结果。包括接口Future和实现Future接口的FutureTask类。   

### Executor框架包含的主要的类与接口
![Executor框架的类与接口](./img/Executor框架的类与接口.png)
Executor是一个接口，它是Executor框架的基础，它将任务的提交与任务的执行分离开来。    
- ThreadPoolExecutor是线程池的核心实现类，用来执行被提交的任务。
- ScheduledThreadPoolExecutor是一个实现类，可以在给定的延迟后运行命令，或者定期执行命令。
ScheduledThreadPoolExecutor比Timer更灵活，功能更强大。     
- Future接口和实现Future接口的FutureTask类，代表异步计算的结果。 
- Runnable接口和Callable接口的实现类，都可以被ThreadPoolExecutor或Scheduled- ThreadPoolExecutor执行。 

![Executor框架的类与接口](./img/Executor框架执行流程.jpg)

### Executor框架的成员
Executor框架的主要成员：`ThreadPoolExecutor`、`ScheduledThreadPoolExecutor`、`Future`接口、`Runnable`接口、`Callable`接口和`Executors`。    
- ThreadPoolExecutor通常使用工厂类Executors来创建。
Executors可以创建3种类型的 `ThreadPoolExecutor`：`SingleThreadExecutor`、`FixedThreadPool`和`CachedThreadPool`。
    - FixedThreadPool适用于为了满足资源管理的需求，而需要限制当前线程数量的应用场景，它适用于负载比较重的服务器。   
        ```text
        public static ExecutorService newFixedThreadPool(int nThreads)  
        public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory)     
         ```
    - SingleThreadExecutor创建使用单个线程的SingleThreadExecutor的API。适用于需要保证顺序地执行各个任务；并且在任意时间点，不会有多个线程是活动的应用场景。    
        ```text
        public static ExecutorService newSingleThreadExecutor()     
        public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory)      
        ```
    - CachedThreadPool是大小无界的线程池，适用于执行很多的短期异步任务的小程序，或者是负载较轻的服务器。      
        ```text
        public static ExecutorService newCachedThreadPool()         
        public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory)      
        ```
- ScheduledThreadPoolExecutor通常使用工厂类`Executors`来创建        
Executors可以创建2种类 型的ScheduledThreadPoolExecutor：ScheduledThreadPoolExecutor，SingleThreadScheduledExecutor    
    - ScheduledThreadPoolExecutor。包含若干个线程的`ScheduledThreadPoolExecutor`。  
     ```text
    适用于需要多个后台线程执行周期任务，同时为了满足资源 管理的需求而需要限制后台线程的数量的应用场景   
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)         
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize,ThreadFactory threadFactory) 
    ```     
    - SingleThreadScheduledExecutor。只包含一个线程的ScheduledThreadPoolExecutor。    
    适用于需要单个后台线程执行周期任务，同时需要保证顺 序地执行各个任务的应用场景。   
    ```text
    public static ScheduledExecutorService newSingleThreadScheduledExecutor()           
    public static ScheduledExecutorService newSingleThreadScheduledExecutor (ThreadFactory threadFactory)   
    ```
- Future接口  
`Future`接口和实现`Future`接口的`FutureTask`类用来表示异步计算的结果。     
```text
<T> Future<T> submit(Callable<T> task)      
<T> Future<T> submit(Runnable task, T result)       
Future<> submit(Runnable task)
```
- Runnable接口和Callable接口 
`Runnable`接口和`Callable`接口的实现类，都可以被`ThreadPoolExecutor`或`ScheduledThreadPoolExecutor`执行。   
它们之间的区别是`Runnable`不会返回结果，而`Callable`可以返回结果。

除了可以自己创建实现`Callable`接口的对象外，还可以使用工厂类`Executors`来把一个`Runnable`包装成一个`Callable`。   
```text
public static Callable<Object> callable(Runnable task) // 假设返回对象Callable1   
public static <T> Callable<T> callable(Runnable task, T result) // 假设返回对象Callable    
```











