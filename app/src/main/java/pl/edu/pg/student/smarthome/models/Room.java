package pl.edu.pg.student.smarthome.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Pawel on 17.09.2018.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
}
