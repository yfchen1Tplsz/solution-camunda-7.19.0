package com.cntaiping.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("doRepair")
public class DoRepairService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("师傅开始上门修理");
        String currentAcitivityName = delegateExecution.getCurrentActivityName();
        System.out.println("当前活动名： "+ currentAcitivityName);
        delegateExecution.setVariable("repairManName","陈师傅");
    }
}
