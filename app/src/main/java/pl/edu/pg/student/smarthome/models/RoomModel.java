package pl.edu.pg.student.smarthome.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Pawel on 17.09.2018.
 */

@Getter
@Setter
@AllArgsConstructor
public class RoomModel {

    private long id;
    private ArrayList<DeviceModel> devices;
    private String info;
}
