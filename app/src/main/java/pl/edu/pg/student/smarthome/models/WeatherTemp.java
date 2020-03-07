package pl.edu.pg.student.smarthome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Pawel on 24.09.2018.
 */

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherTemp {

    private double temp;
    private double pressure;
    private double humidity;
    private double temp_min;
    private double temp_max;
}
