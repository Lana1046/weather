package com.codefromscratch.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {
    public Headline Headline;

    public List<DailyForecast> DailyForecasts;
}