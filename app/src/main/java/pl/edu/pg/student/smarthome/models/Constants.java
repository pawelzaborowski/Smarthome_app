package pl.edu.pg.student.smarthome.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Pawel on 24.09.2018.
 */

public class Constants {

    public static final String OPENWEATHERMAPAPI_KEY = "c052d034d955b18294cfa232c0975a78";

    private static final String BASE_URL = "http://192.168.4.1:80/";

    public static final String ROOMS_URL = BASE_URL+ "api/Rooms";

    public static final String DEVICES_URL = BASE_URL+ "api/Devices";
    public static final String GET_ALL_DEVICES = BASE_URL+ "api/Devices";
    public static final String GET_DEVICE_BY_ID = BASE_URL+ "api/Devices/";
    public static final String GET_DEVICES_FROM_ROOM = BASE_URL+ "api/Devices/GetByRoom?roomID=";

    public static final String GET_ALL_ROOMS = BASE_URL+ "api/Rooms";
    public static final String GET_ROOM_BY_ID = BASE_URL+ "api/Rooms/";

    public static final String POST_NEW_DEVICE_1 = BASE_URL+ "api/Devices/addDevice?name=";
    public static final String POST_NEW_DEVICE_2 = "?status=true";
    public static final String POST_NEW_ROOM = BASE_URL+ "api/Rooms/addRoom?name=";

    public static final String PUT_DEVICE_CHANGE = BASE_URL+ "api/Devices/";
    public static final String PUT_ROOM_CHANGE = "/api/Rooms/";

    public static final String DELETE_DEVICE_BY_ID = BASE_URL+ "api/Devices/";
    public static final String DELETE_ROOM_BY_ID = BASE_URL+ "api/Rooms/";

    public static final String TURN_DEVICE_ON_BY_ID = BASE_URL + "api/DeviceActions/TurnOnLED?device=";
    public static final String TURN_DEVICE_OFF_BY_ID = BASE_URL + "api/DeviceActions/TurnOffLED?device=";


    public static final String TURN_ON_DEVICE = "";
    public static final String TURN_OFF_DEVICE = "";

    public static List<Device> devices = new ArrayList<>();
    public static List<Room> rooms = new ArrayList<>();
    public static Map<String, String> roomsNameId = new HashMap<>();

    public static Boolean ifActiveProfile = false;
    public static String activeProfileName = "";

    public static final String icon_clear_sky_day = "http://openweathermap.org/img/w/01d.png";
    public static final String icon_clear_sky_night = "http://openweathermap.org/img/w/01n.png";
    public static final String icon_few_clouds_day = "http://openweathermap.org/img/w/02d.png";
    public static final String icon_few_clouds_night = "http://openweathermap.org/img/w/02n.png";
    public static final String icon_scattered_clouds_day = "http://openweathermap.org/img/w/03d.png";
    public static final String icon_scattered_clouds_night = "http://openweathermap.org/img/w/03n.png";
    public static final String icon_broken_clouds_day = "http://openweathermap.org/img/w/04d.png";
    public static final String icon_broken_clouds_night = "http://openweathermap.org/img/w/04n.png";
    public static final String icon_shower_rain_day = "http://openweathermap.org/img/w/09d.png";
    public static final String icon_shower_rain_night = "http://openweathermap.org/img/w/09n.png";
    public static final String icon_rain_day = "http://openweathermap.org/img/w/10d.png";
    public static final String icon_rain_night = "http://openweathermap.org/img/w/10n.png";
    public static final String icon_thunderstorm_day = "http://openweathermap.org/img/w/11d.png";
    public static final String icon_thunderstorm_night = "http://openweathermap.org/img/w/11n.png";
    public static final String icon_snow_day = "http://openweathermap.org/img/w/13d.png";
    public static final String icon_snow_night = "http://openweathermap.org/img/w/13n.png";
    public static final String icon_mist_day = "http://openweathermap.org/img/w/50d.png";
    public static final String icon_mist_night = "http://openweathermap.org/img/w/50n.png";

}
