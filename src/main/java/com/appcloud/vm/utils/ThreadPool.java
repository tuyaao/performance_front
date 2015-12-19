package com.appcloud.vm.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//做成单例的
//1.put和take：当试图向满的队列中体添加，或从空的队列中移出元素时，导致线程阻塞。用于将队列当做线程的管理工具来使用。 
//2.add，remove和element，当试图向满的队列中体添加，或从空的队列中移出元素时抛出异常。 
//3.offer，poll和peek，当试图向满的队列中体添加，或从空的队列中移出元素时如果不能完成任务，只是给出一个错误提示（返回null），而不会抛出异常。 
//•阻塞队列的几个变种： 
//1.LinkedBlockingQueue ： 没有上边界 
//2.ArrayBlockingQueue ：构造时需要指定容量 
//3.priorityBlockingQueue:是一个带优先级的队列，而不是先进先出队列 
//4.DelayQueue：包含实现Delayed接口的对象 

    public class ThreadPool {
//    	private static ExecutorService  threadPoolExecutorService = null;
    	private static ThreadPoolExecutor  threadPoolExecutorService = null;
    	
    	public ThreadPool( ){
    		if (null == threadPoolExecutorService ){
//    			threadPoolExecutorService = Executors.newFixedThreadPool(15);
    			threadPoolExecutorService = new ThreadPoolExecutor(15, 15,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());
    			threadPoolExecutorService.prestartAllCoreThreads();
    		}
    	}
    	
    	public static void submitThread(FutureTask futureTask) {  
    		threadPoolExecutorService.submit(futureTask);  
        }  
    	
    	public static void execute(Runnable runnable) {  
    		threadPoolExecutorService.execute(runnable);  
        }  
    	
//        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
//        executor.submit(futureTask);
    	
    	//关闭线程  
        public void closeThreads(){        
            if( threadPoolExecutorService != null ){  
                System.out.println( "关闭线程。");   
				threadPoolExecutorService.shutdown();
				threadPoolExecutorService = null;     
            }  
        }  
	
//	  private BlockingQueue taskQueue = null;
//	  private List<PoolThread> threads = new ArrayList<PoolThread>();
//	  private boolean isStopped = false;
//	  
//	  public ThreadPool(int noOfThreads, int maxNoOfTasks) {
//	    taskQueue = new BlockingQueue(maxNoOfTasks);
//	 
//	    for (int i=0; i<noOfThreads; i++) {
//	      threads.add(new PoolThread(taskQueue));
//	    }
//	    for (PoolThread thread : threads) {
//	      thread.start();
//	    }
//	  }
//	 
//	  public synchronized void execute(Runnable task) {
//	    if(this.isStopped) throw new IllegalStateException("ThreadPool is stopped");
//	    this.taskQueue.add(task);
//	  }
//	 
//	  public synchronized void stop() {
//	    this.isStopped = true;
//	    for (PoolThread thread : threads) {
//	      thread.stop();
//	    }
//	  }


}
