package com.example.loan.service;

import com.example.loan.dto.RepaymentDTO;

import static com.example.loan.dto.RepaymentDTO.*;

public interface RepaymentService {
    Response create(Long applicationId, Request request);
}
