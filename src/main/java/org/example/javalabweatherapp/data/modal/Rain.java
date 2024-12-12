package org.example.javalabweatherapp.data.modal;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Rain {
    @SerializedName("1h")
    private Double speed;
}
