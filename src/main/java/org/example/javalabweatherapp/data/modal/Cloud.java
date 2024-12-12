package org.example.javalabweatherapp.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Cloud {
    @Expose
    @SerializedName("all")
    private Double percent;
}
