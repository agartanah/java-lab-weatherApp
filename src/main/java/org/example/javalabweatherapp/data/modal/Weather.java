package org.example.javalabweatherapp.data.modal;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Weather {
    @Expose
    private String description;

    @Expose
    private String icon;

    public String getIcon() {
        return "https://openweathermap.org/img/wn/" + icon + "@2x.png";
    }
}
