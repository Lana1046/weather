package com.codefromscratch.jsonparsing;
import okhttp3.*;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Json {
    public static void main(String[] args) {
        String apiKey = "nYt6xcL83hcRqRB2xNFafv98kt2mjXUL";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        String town = "Moscow";
        try {
            OkHttpClient client = new OkHttpClient();

            String searchTownKeyUrl = String.format("http://dataservice.accuweather.com/locations/v1/cities/search?q=%s&apikey=%s", town, apiKey);
            Request searchTownKeyRequest = new Request.Builder().url(searchTownKeyUrl).get().build();
            Response searchTownKeyResponse = client.newCall(searchTownKeyRequest).execute();
            String searchTownKeyJsonStr = searchTownKeyResponse.body().string();
            City[] citySearchResponse = objectMapper.readValue(searchTownKeyJsonStr, City[].class);

            String townKey = citySearchResponse[0].Key;

            String getWeatherUrl = String.format("http://dataservice.accuweather.com/forecasts/v1/daily/5day/%s?apikey=%s", townKey, apiKey);
            Request getWeatherRequest = new Request.Builder().url(getWeatherUrl).get().build();
            Response getWeatherResponse = client.newCall(getWeatherRequest).execute();
            String getWeatherJsonStr = getWeatherResponse.body().string();
            WeatherResponse weatherResponse = objectMapper.readValue(getWeatherJsonStr, WeatherResponse.class);

            displayWeather(town, weatherResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void displayWeather(String town, WeatherResponse weather) {
        System.out.println(town);
        System.out.println(weather.Headline.Text);
        for (DailyForecast dayForecast: weather.DailyForecasts)
        {
            System.out.println(new SimpleDateFormat("dd.MM.yyyy").format(dayForecast.Date));
            TemperatureDescription min = dayForecast.Temperature.Minimum;
            TemperatureDescription max = dayForecast.Temperature.Maximum;

            System.out.printf("Minimum: %.2f°C Maximum: %.2f°C\n", farangeitToCelsi(min.Value), farangeitToCelsi(max.Value));
        }
    }

    static Double farangeitToCelsi(Double farangeit) {
        return (farangeit - 32) / 1.8;
    }
}