package com.comak.sce.service;

import com.comak.sce.exception.AccountException;
import com.comak.sce.model.ExchangeData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class AccountServiceTest {

    @Autowired private AccountService accountService;

    @Test
    void getCurrentExchangeRate_getMidNotNullTest() throws AccountException {
        ExchangeData result = accountService.getCurrentExchangeRate("USD");
        Assertions.assertNotNull(result.getRates().stream().findFirst().get().getMid());
    }
}