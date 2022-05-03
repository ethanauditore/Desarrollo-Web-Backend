package com.practicaextra.api.service;

import com.practicaextra.api.dto.DtoExchange;

public interface SvcExchange {

	public DtoExchange getRate(String currency);

}
