package com.example.expenseservice.repository;

import java.security.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.expenseservice.dto.ExpenseDTO;
import com.example.expenseservice.entities.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Optional<Expense> findById(Integer id);

    List<Expense> findByUserId(String userid);

    List<Expense> findByUserIdAndCreatedAtBetween(String userid, Timestamp starTimestamp, Timestamp endtime);

    Optional<Expense> findByUserIdAndExternalId(String userid, String externalid);

}
