package com.example.expenseservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.expenseservice.dto.ExpenseDTO;
import com.example.expenseservice.service.ExpenseService;

@Service
public class ExpenseServiceConsumer {

    private final ExpenseService expenseService;

    public ExpenseServiceConsumer(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDTO expenseDTO) {
        System.out.println(" I am in kafka consumer listen method");
        System.out.println(expenseDTO.toString());

        try {
            this.expenseService.createExpense(expenseDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
