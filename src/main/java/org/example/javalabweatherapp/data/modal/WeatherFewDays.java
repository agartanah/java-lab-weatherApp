package org.example.javalabweatherapp.data.modal;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class WeatherFewDays {
    @Expose
    List<WeatherCity> list;

    List<WeatherDay> weatherDays = new LinkedList<>();

    public void clearWeatherDays() {
        weatherDays.clear();
    }

    public List<WeatherDay> getWeatherDays() {
        if (!weatherDays.isEmpty()) {
            return weatherDays;
        }

        String format = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDateTime currentDate = LocalDateTime.now();
        Integer currentDay = currentDate.getDayOfMonth();

        final Integer[] tempDay = {0};
        final Double[] sumTemp = {0d};
        final Integer[] count = {0};
        final Double[] maxTemp = {-10000d};
        final Double[] minTemp = {10000d};
        final String[] icon = {""};
        final Integer[] day = {0};

        list.forEach((item) -> {
            LocalDateTime weatherDate = LocalDateTime.parse(item.getDateTxt(), formatter);
            int itemDay = weatherDate.getDayOfMonth();

            if (tempDay[0] == 0 && !currentDay.equals(itemDay)) {
                tempDay[0] = itemDay;
            }

            if (tempDay[0] == itemDay) {
                sumTemp[0] += item.getMain().getTemp();
                ++count[0];

                if (maxTemp[0] < item.getMain().getTempMax()) {
                    maxTemp[0] = item.getMain().getTempMax();
                }

                if (minTemp[0] > item.getMain().getTempMin()) {
                    minTemp[0] = item.getMain().getTempMin();
                }

                icon[0] = item.getWeather().getFirst().getIcon();
                day[0] = itemDay;
            } else if (tempDay[0] != 0) {
                tempDay[0] = itemDay;

                WeatherDay weatherDay = new WeatherDay(
                        icon[0],
                        day[0],
                        sumTemp[0] / count[0],
                        maxTemp[0],
                        minTemp[0]
                );

                weatherDays.add(weatherDay);

                icon[0] = item.getWeather().getFirst().getIcon();
                maxTemp[0] = item.getMain().getTempMax();
                minTemp[0] = item.getMain().getTempMin();
                sumTemp[0] = item.getMain().getTemp();
                count[0] = 0;
                day[0] = itemDay;
            }
        });

        return weatherDays;
    }
}
