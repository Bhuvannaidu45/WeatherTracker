package com.app.weatherappli.Controller;

import com.app.weatherappli.Service.WeatherService;
import com.app.weatherappli.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {
        // Fetch weather data from the service
        WeatherData weatherData = weatherService.getWeather(city);

        if (weatherData != null) {

            weatherData.setIconCode("01d"); // Assign a valid icon code
            model.addAttribute("weather", weatherData);
        } else {

            model.addAttribute("error", "Unable to fetch weather data for the city: " + city);
        }

        return "weather";
    }
}
