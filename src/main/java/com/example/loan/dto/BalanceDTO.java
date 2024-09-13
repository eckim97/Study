package com.example.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;

public class BalanceDTO implements Serializable {


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Service
    public static class Request{

        private Long applicationId;

        private BigDecimal entryAmount;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Service
    public static class UpdateRequest{

        private Long applicationId;

        private BigDecimal beforeEntryAmount;

        private BigDecimal afterEntryAmount;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Service
    public static class Response{

        private Long balanceId;

        private Long applicationId;

        private BigDecimal balance;
    }
}
