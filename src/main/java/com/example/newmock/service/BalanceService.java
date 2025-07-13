package com.example.newmock.service;

import com.example.newmock.model.RequestDTO;
import com.example.newmock.model.ResponseDTO;
import com.example.newmock.util.BalanceCalculator;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class BalanceService {

  private final Counter balanceRequestCounter;
  private final Timer balanceCalculationTimer;
  private final Counter currencyCounter;

  public BalanceService(MeterRegistry meterRegistry) {
    this.balanceRequestCounter = Counter.builder("balance_requests_total")
        .description("Total number of balance calculation requests")
        .register(meterRegistry);

    this.balanceCalculationTimer = Timer.builder("balance_calculation_duration")
        .description("Time taken to calculate balance")
        .register(meterRegistry);

    this.currencyCounter = Counter.builder("currency_requests_total")
        .description("Total number of requests by currency")
        .register(meterRegistry);
  }

  public ResponseDTO calculateBalance(RequestDTO request) throws Exception {
    return balanceCalculationTimer.recordCallable(() -> {
      balanceRequestCounter.increment();

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

      currencyCounter.increment();

      BigDecimal balance = BalanceCalculator.randomBalance(maxLimit)
          .setScale(2, RoundingMode.DOWN);

      log.info("Balance calculated for clientId: {}, currency: {}, balance: {}",
          request.getClientId(), currency, balance);

      return new ResponseDTO(
          request.getRqUID(),
          request.getClientId(),
          request.getAccount(),
          currency,
          balance,
          maxLimit.setScale(2, RoundingMode.DOWN)
      );
    });
  }
}
