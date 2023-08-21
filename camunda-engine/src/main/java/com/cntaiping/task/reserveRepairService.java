package com.cntaiping.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class reserveRepairService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("进入预约维修家电任务....");
        String currentActivityName = delegateExecution.getCurrentActivityName();
        //预约具体调用
        //....
        //业务结束
        String processDefinitionId = delegateExecution.getProcessDefinitionId();
        System.out.println("当前活动名称： "+ currentActivityName +"\n流程定义id： "+ processDefinitionId);
    }
}
