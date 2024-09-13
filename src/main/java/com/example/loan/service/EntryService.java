package com.example.loan.service;

import com.example.loan.dto.EntryDTO;

import static com.example.loan.dto.EntryDTO.*;
import static com.example.loan.dto.EntryDTO.Request;
import static com.example.loan.dto.EntryDTO.Response;

public interface EntryService {

    Response create(Long applicationId, Request request);

    Response get(Long applicationId);

    UpdateResponse update(Long entryId, Request request);
}
