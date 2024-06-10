package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

@RestController
//прописываем логику контроллера
public class MainController {
    //будут помогать при заглушке
    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    //метод
    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    // @PostMapping прикрепляем к методу postBalances  и указываем аннотацию с нашим методом:
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        //исключения , описываемс логуку
        try {
            String clientId = requestDTO.getClientId();
            //достаем 1 цифру для условия задачи 0 индекс
            String RqUID = requestDTO.getRqUID();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxlimit;
            String currency;
            Random random = new Random();

            //пишем условие при котором будут выполняться
            //ДЗ Если номер клиента начинается с 8 то валюта счета(currency) доллар - US
            //Если номер клиента начинается с 9 то валюта счета(currency) евро - EU
            //Если начинается с любой другой цифру то валюта счета(currency) рубль - RUB
            if (firstDigit == '8') {
                maxlimit = new BigDecimal(2000);
                currency = new String("US");

            } else if (firstDigit == '9') {
                maxlimit = new BigDecimal(1000);
                currency = new String("EU");

            } else {
                maxlimit = new BigDecimal(10000);
                currency = new String("RUB");
            }
            //рандомный баланс меньше максимального лимита

            // Генерация случайной суммы в пределах от 0 до maxLimit
            double randomBalance = Math.round(random.nextDouble() * maxlimit.doubleValue()*100) /100.0;
            BigDecimal balance = BigDecimal.valueOf(randomBalance);
            //делаем экземпляр класса
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            //будет изначально краснить, в респон поменять на private BigDecimal balance;private BigDecimal maxLimit;

           //responseDTO.setBalance(new BigDecimal(7777));
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxlimit);
            responseDTO.setCurrency(currency);
            //опишем запросы и ответы

            log.error("******* RequestDTO *******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));//пишем как удобно и что хотим
            log.error("******* ResponseDTO *******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            //Возвращаем респонс
            return responseDTO;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
