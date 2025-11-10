package com.example.calculator.controller;


import com.example.calculator.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {
	CalculatorService calculatorService;
	@Autowired
	public CalculatorController(CalculatorService calculatorService){
	this.calculatorService = calculatorService;
}
	@GetMapping("sum")
	int ab(@RequestParam("a") int a, @RequestParam("b") int b) {
	return calculatorService.sum(a, b);
}
}
