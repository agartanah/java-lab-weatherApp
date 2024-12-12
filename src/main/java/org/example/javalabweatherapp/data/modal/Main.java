package org.example.javalabweatherapp.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Main {
    @Expose
    private Double temp;

    @Expose
    @SerializedName("feels_like")
    private Double feelsLike;

    @Expose
    @SerializedName("temp_min")
    private Double tempMin;

    @Expose
    @SerializedName("temp_max")
    private Double tempMax;

    @Expose
    private Double pressure;

    @Expose
    private Double humidity;
}
