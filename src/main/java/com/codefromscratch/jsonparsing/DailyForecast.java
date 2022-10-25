package com.codefromscratch.jsonparsing;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;

public class DailyForecast {
    public Date Date;

    public Temperature Temperature;
}
