package com.cntaiping.config;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {
    @Value("${camunda.rest.url}")
    private String url;

    @Bean
    public ExternalTaskClient taskClient(){
        return ExternalTaskClient.create()
                .baseUrl(url)
                .asyncResponseTimeout(20000)
                .build();
    }
    @Bean
    @ExternalTaskSubscription(topicName = "try_self_repair",processDefinitionKeyIn ={"Process_service_task"},lockDuration = 50000)
    public ExternalTaskHandler doSelfRepair(){
        return
                (externalTask, externalTaskService) -> {
                    System.out.println("尝试自我维修...");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    boolean isFree = (Boolean) externalTask.getVariable("isFree");
                    if(isFree){
                        System.out.println("免费维修..");
                        externalTaskService.handleFailure(externalTask,"维修免费，不用费劲自修","打印点信息",0,5000);
                    }else{
                        System.out.println("自修");
                        externalTaskService.complete(externalTask);
                    }
                };

    }

}
