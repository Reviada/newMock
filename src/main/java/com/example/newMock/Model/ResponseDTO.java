package com.example.newMock.Model;

import lombok.*;

import java.math.BigDecimal;
//вместо геттеров и сеттеров
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ResponseDTO {
    private String rqUID;
    private String clientId;
    private String account;
    private String currency;
    private BigDecimal balance;
    private BigDecimal maxLimit;


}
