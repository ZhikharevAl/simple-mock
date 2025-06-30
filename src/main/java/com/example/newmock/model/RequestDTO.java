package com.example.newmock.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Входящий запрос для расчета баланса")
public class RequestDTO {

  @NotBlank
  @Schema(description = "Уникальный идентификатор запроса")
  @JsonProperty("rqUID")
  private String rqUID;


  @NotNull
  @Size(min = 19, max = 19, message = "clientId должен быть длиной ровно 19 символов")
  @Pattern(regexp = "\\d+", message = "clientId должен содержать только цифры")
  @Schema(description = "ID клиента (19 цифр)")
  private String clientId;

  @NotNull
  @Size(min =20, max = 20, message = "account должен быть длиной ровно 20 символов")
  @Pattern(regexp = "^\\d{20}$", message = "account должен содержать только цифры")
  @Schema(description = "Номер счета (20 цифр)")
  private String account;

  @Schema(description = "Дата открытия счета в формате YYYY-MM-DD")
  private String openDate;
  @Schema(description = "Дата закрытия счета в формате YYYY-MM-DD")
  private String closeDate;
}
