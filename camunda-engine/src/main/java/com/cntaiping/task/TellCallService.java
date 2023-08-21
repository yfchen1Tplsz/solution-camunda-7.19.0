package com.cntaiping.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("tellCall")
public class TellCallService {
    public long getScore(DelegateExecution execution){
        System.out.println("开始电话回访任务...");
        String repairManName = String.valueOf(execution.getVariable("repairManName"));
        System.out.println("开始收集对"+repairManName+"的打分信息...");
        return 100l;
    }

    public void showScore(DelegateExecution execution){
        System.out.println("查看评分任务开启... ");
        String repairManName = String.valueOf(execution.getVariable("repairManName"));
        long score = (long) execution.getVariable("score");
        System.out.println("顾客对"+repairManName+"的评分为： "+score+"分");
    }
}
