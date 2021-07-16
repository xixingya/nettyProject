package tech.xixing.threads;

import java.util.concurrent.*;

/**
 * @author liuzhifei
 * @version 1.0
 * @date 2021/7/5 3:50 PM
 */
public class FutureDemo {

    public static void main(String[] args) throws Exception{
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        final Future<Object> submit = executorService.submit(()->isAdd(true));
        final Object o = submit.get(1, TimeUnit.MILLISECONDS);
        System.out.println("executorService:"+o);
        final CompletableFuture<Boolean> booleanCompletableFuture = CompletableFuture.supplyAsync(() -> isAdd(true));
        System.out.println("CompletableFuture: "+booleanCompletableFuture);

        executorService.shutdown();
    }

    public static Boolean isAdd(Boolean isAdd){

        return isAdd;
    }

}
