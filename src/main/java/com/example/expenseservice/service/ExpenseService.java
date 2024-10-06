package com.example.expenseservice.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expenseservice.dto.ExpenseDTO;
import com.example.expenseservice.entities.Expense;
import com.example.expenseservice.repository.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExpenseService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExpenseRepository expenseRepository;

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    public boolean createExpense(ExpenseDTO expenseDTO) {
        setCurrency(expenseDTO);

        try {
            Expense expense = this.objectMapper.convertValue(expenseDTO, Expense.class);
            logger.info("Creating expense for user id {}" + expense.getUserId());
            this.expenseRepository.save(expense);

            logger.info("Expense created successfuly ");
            return true;
        } catch (Exception e) {

            logger.error("Error creating expense ", e.getMessage());
            return false;

        }
    }

    // in future use distrubuted lock
    // because update consists of three statements
    // fetch from sql ->> busniness logic --> save into sql
    // between this when we are applying business logic then if any user make get
    // request
    // then we have to give old expense object
    public boolean update(ExpenseDTO expenseDTO) {
        Optional<Expense> expense = this.expenseRepository.findByUserIdAndExternalId(expenseDTO.getUserId(),
                expenseDTO.getExternalId());
        if (expense.isPresent()) {
            Expense expense1 = expense.get();
            expense1.setCurrency(
                    Strings.isNotBlank(expenseDTO.getCurrency()) ? expenseDTO.getCurrency() : expense1.getCurrency());
            expense1.setMerchant(
                    Strings.isNotBlank(expenseDTO.getMerchant()) ? expenseDTO.getMerchant() : expense1.getMerchant());
            expense1.setAmount(expenseDTO.getAmount());
            this.expenseRepository.save(expense1);
            return true;
        } else {
            return false;
        }
    }

    public List<ExpenseDTO> getExpenses(String userid) {
        List<Expense> expenses = this.expenseRepository.findByUserId(userid);
        if (!expenses.isEmpty()) {
            List<ExpenseDTO> result = expenses.stream().map(expense -> this.mapper.map(expense, ExpenseDTO.class))
                    .collect(Collectors.toList());
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    private void setCurrency(ExpenseDTO expenseDTO) {
        if (Objects.isNull(expenseDTO.getCurrency())) {
            expenseDTO.setCurrency("inr");
        }
    }

}
