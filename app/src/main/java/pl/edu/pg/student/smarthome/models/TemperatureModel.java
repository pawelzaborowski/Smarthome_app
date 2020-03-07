package pl.edu.pg.student.smarthome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureModel {

    private double Temperature;
    private double Distance;

    public TemperatureModel(String url){
        if(url != null) {
//            String[] parts = url.split(":");
//            Temperature = Double.parseDouble(parts[0]);
//            Distance = Double.parseDouble(parts[1]);

            try {
                JSONObject obj = new JSONObject(url);
                this.Temperature = Double.parseDouble(obj.getString("Temperature"));
                this.Distance = Double.parseDouble(obj.getString("Distance"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


//
////    @JsonDeserialize
//    public double getTemperature(){
//        return Double.parseDouble(Temperature);
//    }
//
////    @JsonDeserialize
//    public double getDistance(){
//        return Double.parseDouble(Distance);
//    }
//
//    public void setTemperature(double t){
//        Temperature = String.valueOf(t);
//    }
//
//    public void setDistance(double d){
//        Distance = String.valueOf(d);
//    }
//
//    public TemperatureModel(double t, double d){
//        Temperature = String.valueOf(t);
//        Distance = String.valueOf(d);
//    }
}
