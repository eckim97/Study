package com.example.loan.service;

import com.example.loan.dto.CounselDTO;
import com.example.loan.dto.CounselDTO.Response;

public interface CounselService {
    Response create(CounselDTO.Request request);

}
