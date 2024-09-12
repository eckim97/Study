package com.example.loan.service;

import static com.example.loan.dto.EntryDTO.Request;
import static com.example.loan.dto.EntryDTO.Response;

public interface EntryService {

    Response create(Long applicationId, Request request);
}
