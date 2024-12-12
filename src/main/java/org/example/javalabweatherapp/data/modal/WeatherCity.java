package org.example.javalabweatherapp.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WeatherCity {
    @Expose
    private String name;

    @Expose
    private List<Weather> weather;

    @Expose
    private Main main;

    @Expose
    private Wind wind;

    @Expose
    @SerializedName("clouds")
    private Cloud cloud;

    @Expose
    private Rain rain;

    @Expose
    private Snow snow;

    @SerializedName("dt_txt")
    private String dateTxt;
}
