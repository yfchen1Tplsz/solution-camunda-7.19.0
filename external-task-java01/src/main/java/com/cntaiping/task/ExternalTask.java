package com.cntaiping.task;

import ch.qos.logback.core.util.TimeUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExternalTask {

    private final ExternalTaskClient taskClient;
    @PostConstruct
    public void handleShoppingCart(){
        taskClient.subscribe("shopping_cart")
                .processDefinitionKey("Process_shopping")
                .lockDuration(2000)
                .handler(((externalTask, externalTaskService) -> {
                    log.info("订阅加入购物车任务..");
                    Map<String,Object> goodVariable = Variables.createVariables()
                            .putValue("goods","东北往事回忆录")
                            .putValue("count",2)
                            .putValue("price",59.9);
                    externalTaskService.complete(externalTask, goodVariable);
                })).open();
    }

    @PostConstruct
    public void handlePayment(){
        taskClient.subscribe("pay")
                .processDefinitionKey("Process_shopping")
                .lockDuration(2000)
                .handler(((externalTask, externalTaskService) -> {
                    Object goods = externalTask.getVariable("goods");
                    Object count = externalTask.getVariable("count");
                    Object price = externalTask.getVariable("price");
                    String msg = StrUtil.format("商品信息如下：\n商品名：{}\n 商品数量：{}\n 商品单价: {}", goods, count, price);
                    log.info(msg);
                    Double total = Double.parseDouble( count.toString())*Double.parseDouble(price.toString());
                    Map<String,Object> paymentVariable = Variables.createVariables()
                            .putValue("total",total)
                            .putValue("address","沈阳大街45号")
                            .putValue("user","yfchen");
                    externalTaskService.complete(externalTask, paymentVariable);
                })).open();
    }


    @PostConstruct
    public void handleDelivery(){
        taskClient.subscribe("logistic_delivery")
                .processDefinitionKey("Process_shopping")
                .lockDuration(2000)
                .handler(((externalTask, externalTaskService) -> {
                    Object total = externalTask.getVariable("total");
                    Object address = externalTask.getVariable("address");
                    Object user = externalTask.getVariable("user");
                    String msg = StrUtil.format("订单信息如下：\n总价：{}\n 收货地址：{}\n 用户: {}", total, address, user);
                    log.info(msg);
                    externalTaskService.complete(externalTask);
                })).open();
    }



}
