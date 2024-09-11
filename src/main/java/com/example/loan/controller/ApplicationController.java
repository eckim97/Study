package com.example.loan.controller;

import com.example.loan.dto.ResponseDTO;
import com.example.loan.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.loan.dto.ApplicationDTO.Request;
import static com.example.loan.dto.ApplicationDTO.Response;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationController extends AbstractController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(applicationService.create(request));
    }

    @GetMapping("/{applicationId}")
    public ResponseDTO<Response> get(@PathVariable Long applicationId) {
        return ok(applicationService.get(applicationId));
    }
}
