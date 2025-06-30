package com.example.newmock.service;

import com.example.newmock.model.RequestDTO;
import com.example.newmock.model.ResponseDTO;
import com.example.newmock.util.BalanceCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BalanceService {

  public ResponseDTO calculateBalance(RequestDTO request) {
    char firstDigit = request.getClientId().charAt(0);
    BigDecimal maxLimit;
    String currency;

    switch (firstDigit) {
      case '8' -> {
        currency = "US";
        maxLimit = new BigDecimal("2000.00");
      }
      case '9' -> {
        currency = "EU";
        maxLimit = new BigDecimal("1000.00");
      }
      default -> {
        currency = "RUB";
        maxLimit = new BigDecimal("10000.00");
      }
    }

    BigDecimal balance = BalanceCalculator.randomBalance(maxLimit).setScale(2, RoundingMode.DOWN);

    return new ResponseDTO(
        request.getRqUID(),
        request.getClientId(),
        request.getAccount(),
        currency,
        balance,
        maxLimit.setScale(2, RoundingMode.DOWN)
    );
  }
}
