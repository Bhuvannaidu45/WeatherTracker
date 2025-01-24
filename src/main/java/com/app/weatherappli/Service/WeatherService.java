package com.app.weatherappli.Service;

import com.app.weatherappli.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String apiKey = "9efa7abf64ef48d3bcf40938242612";
    private final String baseUrl = "https://api.weatherapi.com/v1/current.json";

    public WeatherData getWeather(String city) {
        String url = baseUrl + "?key=" + apiKey + "&q=" + city + "&aqi=no";
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Fetch the weather data from the API response
            String response = restTemplate.getForObject(url, String.class);
            JsonNode weatherNode = new ObjectMapper().readTree(response);

            // Parse the data
            WeatherData weatherData = new WeatherData();
            weatherData.setCity(weatherNode.path("location").path("name").asText());
            weatherData.setCountry(weatherNode.path("location").path("country").asText());
            weatherData.setTemperature(weatherNode.path("current").path("temp_c").asInt());
            weatherData.setCondition(weatherNode.path("current").path("condition").path("text").asText());
            weatherData.setIconCode(weatherNode.path("current").path("condition").path("icon").asText());
            weatherData.setHumidity(weatherNode.path("current").path("humidity").asInt());
            weatherData.setWindSpeed(weatherNode.path("current").path("wind_kph").asInt());
            weatherData.setFeelsLike(weatherNode.path("current").path("feelslike_c").asInt());

            return weatherData;
        } catch (Exception e) {
            // Handle errors and return default error message
            return null;
        }
    }
}
