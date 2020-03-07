package pl.edu.pg.student.smarthome.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Pawel on 18.09.2018.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    private String id;
    @JsonProperty("roomID")
    private String roomId;
    private String name;

//    @JsonProperty("additionalInfo")
    @JsonIgnore
    private String additionalInfo;

    @JsonIgnore
    private TemperatureModel temperatureModel = new TemperatureModel(additionalInfo);

    @JsonIgnore
    String temp;
    @JsonIgnore
    String dist;

    @JsonIgnore
    private Map<String, String> info;

    private Boolean status;
    private String ip;

    @JsonIgnore
    private int positionInList;
}
