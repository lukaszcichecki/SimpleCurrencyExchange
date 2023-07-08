package com.comak.sce.service;

import com.comak.sce.dto.AccountDto;
import com.comak.sce.entity.Account;
import com.comak.sce.exception.AccountException;
import com.comak.sce.model.ExchangeData;
import com.comak.sce.repository.AccountDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountDataRepository accountDataRepository;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;

    private static final String NBP_API_URL = "https://api.nbp.pl/api/exchangerates/rates/A/";

    public AccountDto getAccountById(long id) throws AccountException {
        Account found = findAccountById(id);
        return mapper.map( found, AccountDto.class );
    }

    public AccountDto viewAccountByIdInCurrencyCode(long id, String code) throws AccountException {
        Account found = findAccountById( id );
        AccountDto accountDto = mapper.map( found, AccountDto.class );
        accountDto.setAccountBalance(
            exchangeCurrency(accountDto.getAccountBalance(), accountDto.getCurrentCurrencyCode(), code )
        );
        accountDto.setCurrentCurrencyCode(code);
        return accountDto;
    }

    public AccountDto createAccount(AccountDto accountDto) throws AccountException {
        accountDto.setAccountBalance(
            exchangeCurrency(accountDto.getAccountBalance(), "PLN", "USD" )
        );
        accountDto.setCurrentCurrencyCode("USD");
        Account account = mapper.map( accountDto, Account.class );
        Account saved = accountDataRepository.save(account);
        return mapper.map( saved, AccountDto.class );
    }

    public AccountDto exchangeAccountBalance(Long id, String code) throws AccountException {
        Account found = findAccountById( id );
        BigDecimal balance = exchangeCurrency( found.getBalance(), found.getCurrentCurrencyCode(), code);
        found.setBalance( balance );
        found.setCurrentCurrencyCode( code );
        final Account saved = accountDataRepository.save( found );
        return mapper.map( saved, AccountDto.class );
    }

    public BigDecimal exchangeCurrency(BigDecimal balance, String currentCode, String newCode)
        throws AccountException
    {
        if (! newCode.equals("PLN") && ! newCode.equals("USD"))
            throw new AccountException("Only USD and PLN currency code is accepted !");

        if (! currentCode.equals(newCode)) {
            BigDecimal usdExchangeRate = getUsdExchangeMidRate();
            balance = countBalance( balance, newCode, usdExchangeRate );
        }
        return balance;
    }

    private Account findAccountById(long id) throws AccountException {
        return accountDataRepository
            .findById( id )
            .orElseThrow( () -> new AccountException("Account not found for id: "+ id));
    }

    public ExchangeData getCurrentExchangeRate(String currencyCode) throws AccountException {
        try {
            ResponseEntity<ExchangeData> response = restTemplate
                .exchange(NBP_API_URL + currencyCode, HttpMethod.GET, HttpEntity.EMPTY, ExchangeData.class);
            return response.getBody();

        } catch (RestClientException exception) {
            throw new AccountException( "Exchange rate webApi error: " + exception.getMessage() );
        }

    }

    private BigDecimal countBalance( BigDecimal value, String code, BigDecimal exchangeRate ) {
        value = value.setScale(2, RoundingMode.HALF_EVEN);
        return code.equals("USD")
            ? value.divide( exchangeRate, RoundingMode.HALF_EVEN ).setScale(2, RoundingMode.HALF_EVEN)
            : value.multiply( exchangeRate ).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getUsdExchangeMidRate() throws AccountException {
        return getCurrentExchangeRate("USD")
            .getRates().stream()
            .findFirst()
            .orElseThrow( () -> new AccountException("Currency exchange error") )
            .getMid();
    }

}
