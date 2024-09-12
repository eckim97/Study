package com.example.loan.controller;


import com.example.loan.dto.ResponseDTO;
import com.example.loan.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.loan.dto.EntryDTO.Request;
import static com.example.loan.dto.EntryDTO.Response;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/applications")
public class InternalController extends AbstractController{
    private final EntryService entryService;

    @PostMapping("{applicationId}/entries")
    public ResponseDTO<Response> create(@PathVariable Long applicationId, @RequestBody Request request) {
        return ok(entryService.create(applicationId, request));
    }

    @GetMapping("{applicationId}/entries")
    public ResponseDTO<Response> get(@PathVariable Long applicationId) {
        return ok(entryService.get(applicationId));
    }
}
