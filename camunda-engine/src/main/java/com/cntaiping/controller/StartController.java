package com.cntaiping.controller;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartController {
    @Autowired
    private IdentityService idService;
    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/start/{processKey}")
    public ResponseEntity<Void> stratProcess(@PathVariable("processKey") String processKey,@RequestParam boolean isFree){
-        idService.setAuthenticatedUserId("yfchen");
        VariableMap variables = Variables.createVariables();
        variables.put("isFree",isFree);
        runtimeService.startProcessInstanceByKey(processKey,variables);
        return ResponseEntity.ok().build();
    }
}
