package com.example.loan.service;

import static com.example.loan.dto.CounselDTO.*;


public interface CounselService {
    Response create(Request request);

    Response get(Long counselId);

    Response update(Long counselId, Request request);
}
