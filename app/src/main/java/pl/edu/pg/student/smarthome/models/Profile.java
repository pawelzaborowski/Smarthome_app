package pl.edu.pg.student.smarthome.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private int id;
    private String name;
    private List<Device> devices;
    private Boolean active = false;

    public static final String TABLE_NAME = "profiles";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DEVICES = "devices";
    public static final String COLUMN_STATUS = "status";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
//                    + COLUMN_STATUS + " TEXT,"
                    + COLUMN_DEVICES + " TEXT"
                    + ")";
}