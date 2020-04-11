package com.parkingmanagement.utils;

import com.alibaba.fastjson.JSONObject;
import com.parkingmanagement.dao.OrderCarportDao;
import com.parkingmanagement.entity.OrderCarport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Component
public class DelayQueueManager implements CommandLineRunner {

    @Autowired
    private OrderCarportDao orderCarportDao;

    private DelayQueue<DelayTask> delayQueue = new DelayQueue<>();

    /**
     * 加入到延时队列中
     * @param task
     */
    public void put(DelayTask task) {
        delayQueue.put(task);
    }

    /**
     * 取消延时任务
     * @param task
     * @return
     */
    public boolean remove(DelayTask task) {
        return delayQueue.remove(task);
    }

    /**
     * 取消延时任务
     * @param taskid
     * @return
     */
    public boolean remove(String taskid) {
        return remove(new DelayTask(new TaskBase(taskid), 0));
    }

    @Override
    public void run(String... args) throws Exception {
        Executors.newSingleThreadExecutor().execute(new Thread(this::excuteThread));
    }

    /**
     * 延时任务执行线程
     */
    private void excuteThread() {
        while (true) {
            try {
                DelayTask task = delayQueue.take();
                processTask(task);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * 内部执行延时任务
     * @param task
     */
    private void processTask(DelayTask task) {
        String identifier = task.getData().getIdentifier();
        OrderCarport orderCarport = JSONObject.parseObject(identifier, OrderCarport.class);
        //去数据库中查看  这个预约记录有没有被执行
        OrderCarport dbOrderCarport =   orderCarportDao.findByTime(orderCarport.getOrderCarportTime());
        if (dbOrderCarport.getOrderCarportStatus() == Constant.ORDER_STATUS_ING){
            //用户违约
            dbOrderCarport.setOrderCarportStatus(Constant.ORDER_STATUS_FAILED);
            orderCarportDao.update(dbOrderCarport);
        }

    }
}
