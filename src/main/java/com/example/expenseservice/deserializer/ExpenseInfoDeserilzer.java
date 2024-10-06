package com.example.expenseservice.deserializer;

import org.apache.kafka.common.serialization.Deserializer;

import com.example.expenseservice.dto.ExpenseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExpenseInfoDeserilzer implements Deserializer<ExpenseDTO> {

    @Override
    public ExpenseDTO deserialize(String topic, byte[] data) {
        ExpenseDTO expenseDTO = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String rawDATA = new String(data);
            System.out.println("RAW Json data" + rawDATA);
            expenseDTO = mapper.readValue(rawDATA, ExpenseDTO.class);
        } catch (Exception e) {
            System.err.println("Expense cannot be deserialized: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
        return expenseDTO;
    }

}
