package com.example.expenseservice.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ExpenseDTO {

    @JsonProperty(value = "external_id")
    private String externalId;

    @Nonnull
    @JsonProperty(value = "amount")
    private String amount;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "merchant")
    private String merchant;

    @JsonProperty(value = "currency")
    private String currency;

    @JsonProperty(value = "created_at")
    private Timestamp createdAt;

    public ExpenseDTO(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            ExpenseDTO expense = mapper.readValue(json, ExpenseDTO.class);
            this.externalId = expense.externalId;
            this.amount = expense.amount;
            this.userId = expense.userId;
            this.merchant = expense.merchant;
            this.currency = expense.currency;
            this.createdAt = expense.createdAt;
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ExpenseDto from JSON", e);
        }
    }

}
