package com.example.newmock.util;

import java.math.BigDecimal;
import java.util.Random;

public class BalanceCalculator {
  private static final Random RANDOM = new Random();

  public static BigDecimal randomBalance(BigDecimal maxLimit) {
    return BigDecimal.valueOf(RANDOM.nextDouble()).multiply(maxLimit);
  }
}
