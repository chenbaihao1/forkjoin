package com.weixinxk.mcenter.controller;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * forkJoin任务粒度拆分
 */
class CallableTest {
    public static void main(String[] args) throws Exception {
        int[] array  = {1, 2, 3, 4, 5, 6, 7, 8, 9,10,11};
        ForkTak forkTak = new ForkTak(array, 0, array.length);
        ForkJoinPool joinPool = new ForkJoinPool();
        joinPool.execute(forkTak);
        do {
            TimeUnit.MILLISECONDS.sleep(5);
        }while (!forkTak.isDone());

        // 关闭线程池
        joinPool.shutdown();
    }
}

class ForkTak extends RecursiveAction {
    private int[] src; //表示我们要实际统计的数组
    private int fromIndex;//开始统计的下标
    private int toIndex;//统计到哪里结束的下标
    private static int MAX_COUNT = 3;

    public ForkTak(int[] src, int fromIndex, int toIndex) {
        this.src = src;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    protected void compute() {
        if((toIndex - fromIndex) <= MAX_COUNT){
            System.out.println(src[toIndex-1]);
        }else{
            int mid = (toIndex+fromIndex) >>> 1;
            System.out.println("mid:"+mid);
            ForkTak leftTask = new ForkTak(src, fromIndex, mid);
            ForkTak rightTask = new ForkTak(src, mid + 1, toIndex);
            invokeAll(leftTask, rightTask);
        }
    }
}




