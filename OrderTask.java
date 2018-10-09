package com.weixinxk.mcenter.controller;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


/**
 * forkJoin多任务并行处理
 */
public class OrderTask extends RecursiveTask<OrderInfo> {
    @Override
    protected OrderInfo compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        // 定义其他五种并行TasK
        CustomerTask customerTask = new CustomerTask();
        TenantTask tenantTask = new TenantTask();
        DiscountTask discountTask = new DiscountTask();
        FoodTask foodTask = new FoodTask();
        OtherTask otherTask = new OtherTask();
        invokeAll(customerTask, tenantTask, discountTask, foodTask, otherTask);
        OrderInfo orderInfo = new OrderInfo(customerTask.join(), tenantTask.join(), discountTask.join(), foodTask.join(), otherTask.join());
        return orderInfo;
    }
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() -1 );
        OrderInfo invoke = forkJoinPool.invoke(new OrderTask());
        System.out.println(forkJoinPool.invoke(new OrderTask()));
    }
}
class CustomerTask extends RecursiveTask<CustomerInfo>{

    @Override
    protected CustomerInfo compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        return new CustomerInfo();
    }
}
class TenantTask extends RecursiveTask<TenantInfo>{

    @Override
    protected TenantInfo compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        return new TenantInfo();
    }
}
class DiscountTask extends RecursiveTask<DiscountInfo>{

    @Override
    protected DiscountInfo compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        return new DiscountInfo();
    }
}
class FoodTask extends RecursiveTask<FoodListInfo>{

    @Override
    protected FoodListInfo compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        return new FoodListInfo();
    }
}
class OtherTask extends RecursiveTask<OtherInfo> {

    @Override
    protected OtherInfo compute() {
        System.out.println("执行"+ this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
        return new OtherInfo();
    }
}

class OtherInfo{}

class FoodListInfo{}

class DiscountInfo{}

class CustomerInfo{}

class TenantInfo{}

class OrderInfo{
    private CustomerInfo customerInfo;
    private TenantInfo tenantInfo;
    private DiscountInfo discountInfo;
    private FoodListInfo foodListInfo;
    private OtherInfo otherInfo;


    public OrderInfo() {
    }

    public OrderInfo(CustomerInfo customerInfo, TenantInfo tenantInfo, DiscountInfo discountInfo, FoodListInfo foodListInfo, OtherInfo otherInfo) {
        this.customerInfo = customerInfo;
        this.tenantInfo = tenantInfo;
        this.discountInfo = discountInfo;
        this.foodListInfo = foodListInfo;
        this.otherInfo = otherInfo;
    }
}
