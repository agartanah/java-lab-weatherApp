package org.example.javalabweatherapp.data.modal;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Wind {
    @Expose
    private Double speed;

    @Expose
    private Double deg;

    public String getDirection() {
        return degToDirection(this.deg);
    }

    private String degToDirection(Double deg) {
        if (deg >= 0 && deg < 22.5) {
            return "Север";
        } else if (deg >= 22.5 && deg < 67.5) {
            return "Северо-восток";
        } else if (deg >= 67.5 && deg < 112.5) {
            return "Восток";
        } else if (deg >= 112.5 && deg < 157.5) {
            return "Юго-восток";
        } else if (deg >= 157.5 && deg < 202.5) {
            return "Юг";
        } else if (deg >= 202.5 && deg < 247.5) {
            return "Юго-запад";
        } else if (deg >= 247.5 && deg < 292.5) {
            return "Запад";
        } else if (deg >= 292.5 && deg < 337.5) {
            return "Северо-запад";
        } else {
            return "Север";
        }
    }
}
