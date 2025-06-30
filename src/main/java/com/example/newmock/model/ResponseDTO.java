package com.example.newmock.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ с данными по балансу и лимиту")
public class ResponseDTO {

  @JsonProperty("rqUID")
  @Schema(description = "Уникальный идентификатор запроса")
  private String rqUID;
  @Schema(description = "Уникальный id клиента (19 цифр)")
  private String clientId;
  @Schema(description = "Номер счета (20 цифр)")
  private String account;
  @Schema(description = "Валюта счета (US, EU, RUB)")
  private String currency;
  @Schema(description = "Баланс счета")
  private BigDecimal balance;
  @Schema(description = "Максимальный лимит по счету")
  private BigDecimal maxLimit;
}
