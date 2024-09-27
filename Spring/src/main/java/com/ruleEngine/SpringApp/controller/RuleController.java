package com.ruleEngine.SpringApp.controller;

import com.ruleEngine.SpringApp.model.ASTNode;
import com.ruleEngine.SpringApp.model.Rule;
import com.ruleEngine.SpringApp.repository.RuleRepository;
import com.ruleEngine.SpringApp.service.EvaluateRequestDTO;
import com.ruleEngine.SpringApp.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080") // Allow requests from this origin
@RequestMapping("/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private RuleRepository ruleRepository;
    
    @GetMapping("/getter")
    public String getter() {
    	return "it's done";
    }
    
    
    // Endpoint to create a rule
    @PostMapping("/create")
    public ASTNode createRule(@RequestBody String ruleString) {
    	ASTNode ruleAst=ruleService.createRule(ruleString);
    	String expression=ruleAst.toString();
    	        Rule rule = new Rule(ruleString);
    	        ruleRepository.save(rule);
    	        
    	        return ruleAst;
    }
    
   

    // Endpoint to combine rules
    @PostMapping("/combine")
    public ASTNode combineRules(@RequestBody Map<String, ASTNode> rules) {
        ASTNode rule1 = rules.get("rule1");
        ASTNode rule2 = rules.get("rule2");
        return ruleService.combineRules(rule1, rule2);
    }

     // Endpoint to evaluate rule
     @PostMapping("/evaluate")
     public ResponseEntity<Boolean> evaluateRule(@RequestBody EvaluateRequestDTO request) {
        Map<String, Object> data = request.getData();
        ASTNode rule = request.getRule();
        boolean result = ruleService.evaluateRule(rule, data);
        System.out.println(rule);

        return ResponseEntity.ok(result);
    }

}
