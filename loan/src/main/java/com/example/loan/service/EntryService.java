package com.example.loan.service;

import static com.example.loan.dto.EntryDTO.*;

public interface EntryService {

    Response create(Long applicationId, Request request);

    Response get(Long applicationId);

    UpdateResponse update(Long entryId, Request request);

    void delete(Long entryId);
}
