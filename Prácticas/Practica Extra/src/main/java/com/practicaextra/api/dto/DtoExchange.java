package com.practicaextra.api.dto;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

@Entity
public class DtoExchange {

    @JsonIgnore
    private String url_str;

    @JsonProperty("rate")
    private String req_result;

    public DtoExchange(String currency) {
        url_str = String.format("https://v6.exchangerate-api.com/v6/042cd833ba0cd62c9fc02546/pair/%s/MXN", currency);
    }

    public String getRate() {
        this.req_result = "";
        try {
            URL url = new URL(url_str);
            InputStream in = url.openStream();
            JsonObject jsonobj = JsonParser.parseReader(new InputStreamReader(in)).getAsJsonObject();
            req_result = jsonobj.get("conversion_rate").getAsString();
        } catch (MalformedURLException e) {
            // Lo ignoramos
        } catch (IOException e) {
            // Lo ignoramos
        }
        return req_result;
    }
}
