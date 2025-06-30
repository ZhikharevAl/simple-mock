package com.example.newmock.controller;

import com.example.newmock.model.RequestDTO;
import com.example.newmock.model.ResponseDTO;
import com.example.newmock.service.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/info")
@Tag(name = "Баланс", description = "API расчета баланса и лимита по клиенту")
public class BalanceController {

  private final BalanceService balanceService;

  public BalanceController(BalanceService balanceService) {
    this.balanceService = balanceService;
  }


  @Operation(summary = "Рассчитать баланс и лимит по clientId")
  @PostMapping(value = "/postBalances", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseDTO> postBalances(@Valid @RequestBody RequestDTO request) {
    log.info("[RQ] {}", request);
    ResponseDTO response = balanceService.calculateBalance(request);
    log.info("[RS] {}", response);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/health")
  @Operation(summary = "Health check")
  public String health() {
    return "OK";
  }
}
