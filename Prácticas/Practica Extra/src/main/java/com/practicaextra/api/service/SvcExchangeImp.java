package com.practicaextra.api.service;

import com.practicaextra.api.dto.DtoExchange;
import com.practicaextra.exception.ApiException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SvcExchangeImp implements SvcExchange {

    @Override
    public DtoExchange getRate(String currency) {
        DtoExchange dtoExchange = new DtoExchange(currency);
        if (dtoExchange.getRate().isEmpty())
			throw new ApiException(HttpStatus.NOT_FOUND, "No se encuentra disponible la moneda de cambio");
        return dtoExchange;
    }

}
