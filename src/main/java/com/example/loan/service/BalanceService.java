package com.example.loan.service;

import com.example.loan.dto.BalanceDTO.*;

import static com.example.loan.dto.BalanceDTO.*;

public interface BalanceService {

    Response create(Long applicationId, CreateRequest request);

    Response update(Long applicationId, UpdateRequest request);

    Response repaymentUpdate(Long applicationId, RepaymentRequest request);
}
