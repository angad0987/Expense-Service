package com.example.expenseservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expenseservice.dto.ExpenseDTO;
import com.example.expenseservice.response.ExpenseResponse;
import com.example.expenseservice.service.ExpenseService;

import io.micrometer.common.lang.NonNull;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/v1/expense")
    public ResponseEntity<List<ExpenseDTO>> getExpense(@RequestHeader(value = "X-User-ID") @NonNull String userid) {
        try {
            System.out.println(userid);
            List<ExpenseDTO> list = this.expenseService.getExpenses(userid);
            return new ResponseEntity<List<ExpenseDTO>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/v1/expense")
    public ResponseEntity<String> saveExpense(@RequestHeader(value = "X-User-ID") @NonNull String userid,
            @RequestBody ExpenseDTO expenseDTO) {
        System.out.println("user id in controller : " + userid);
        expenseDTO.setUserId(userid);
        boolean b = this.expenseService.createExpense(expenseDTO);
        if (b) {
            return new ResponseEntity<>("Expense created successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error while creating the expense!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
