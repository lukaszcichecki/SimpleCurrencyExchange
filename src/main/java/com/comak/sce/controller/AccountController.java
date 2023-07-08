package com.comak.sce.controller;

import com.comak.sce.dto.AccountDto;
import com.comak.sce.exception.AccountException;
import com.comak.sce.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
@Validated
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public AccountDto getAccountData(
        @PathVariable @Positive(message = "Account id must ber number grater than 0") Long id
    ) throws AccountException {
        return accountService.getAccountById( id );
    }

    @GetMapping("/{id}/{code}")
    public AccountDto viewAccountDataInCurrencyCode(
        @PathVariable @Positive(message = "Account id must ber number grater than 0") Long id,
        @PathVariable @NotBlank(message = "Currency Code is mandatory !") String code
    ) throws AccountException {
        return accountService.viewAccountByIdInCurrencyCode( id, code );
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid AccountDto accountDto)
        throws AccountException {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/exchange/{code}")
    public AccountDto exchangeAccountBalance(
        @PathVariable @Positive(message = "Account id must ber number grater than 0 !") Long id,
        @PathVariable @NotBlank(message = "Currency Code is mandatory !") String code
    ) throws AccountException {
        return accountService.exchangeAccountBalance(id, code);
    }
}
