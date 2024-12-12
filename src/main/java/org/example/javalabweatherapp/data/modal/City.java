package org.example.javalabweatherapp.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

@Data
public class City {
    private String name;

    @Expose
    @SerializedName("local_names")
    private Map<String, String> localNames;  // Используем Map для динамического хранения локализованных имен

    public String getName() {
        if (localNames != null && localNames.containsKey("ru")) {
            return localNames.get("ru");
        }

        return name;
    }
}
