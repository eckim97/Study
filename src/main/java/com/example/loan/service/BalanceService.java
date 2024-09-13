package com.example.loan.service;

import com.example.loan.dto.BalanceDTO;
import com.example.loan.dto.BalanceDTO.UpdateRequest;

import static com.example.loan.dto.BalanceDTO.Request;
import static com.example.loan.dto.BalanceDTO.Response;

public interface BalanceService {

    Response create(Long applicationId, Request request);

    Response update(Long applicationId, UpdateRequest request);
}
