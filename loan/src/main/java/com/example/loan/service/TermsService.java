package com.example.loan.service;

import java.util.List;

import static com.example.loan.dto.TermsDTO.*;

public interface TermsService {

    Response create(Request request);

    List<Response> getAll();
}
