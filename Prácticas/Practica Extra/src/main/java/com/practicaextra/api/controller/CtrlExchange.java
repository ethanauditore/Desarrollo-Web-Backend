package com.practicaextra.api.controller;

import com.practicaextra.api.dto.DtoExchange;
import com.practicaextra.api.service.SvcExchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange-rate")
public class CtrlExchange {

    @Autowired
    SvcExchange svcExchange;

    @GetMapping("/{currency}")
    public ResponseEntity<DtoExchange> getRate(@PathVariable("currency") String currency) {
        return new ResponseEntity<>(svcExchange.getRate(currency), HttpStatus.OK);
    }
}
