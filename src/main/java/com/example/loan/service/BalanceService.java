package com.example.loan.service;

import static com.example.loan.dto.BalanceDTO.Request;
import static com.example.loan.dto.BalanceDTO.Response;

public interface BalanceService {

    Response create(Long applicationId, Request request);
}
