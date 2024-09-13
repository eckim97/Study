package com.example.loan.service;

import com.example.loan.dto.RepaymentDTO;

import java.util.List;

import static com.example.loan.dto.RepaymentDTO.*;

public interface RepaymentService {
    Response create(Long applicationId, Request request);

    List<ListResponse> get(Long applicationId);

    UpdateResponse update(Long repaymentId, Request request);
}
