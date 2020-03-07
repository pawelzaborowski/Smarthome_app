package pl.edu.pg.student.smarthome;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pl.edu.pg.student.smarthome.models.Device;
import pl.edu.pg.student.smarthome.models.Room;
import pl.edu.pg.student.smarthome.models.TemperatureModel;

public class Bootstrap {

    public static List<Device> InitDevices() throws JSONException {
        List<Device> devices = new ArrayList<>();

        Room sypialnia = new Room();
        Room kuchnia = new Room();
        Room pokoj = new Room();

        Device lampa1 = new Device();
        Device lampa2 = new Device();
        Device temp1 = new Device();
        Device temp2 = new Device();
        Device lampa3 = new Device();
        Device lampa4 = new Device();
        Device temp3 = new Device();
        Device temp4 = new Device();

        sypialnia.setName("sypialnia");
        sypialnia.setId("1");
        kuchnia.setName("kuchnia");
        kuchnia.setId("2");
        pokoj.setName("pokoj");
        pokoj.setId("3");

        lampa1.setName("lampa1");
        lampa1.setStatus(true);
        lampa1.setRoomId("1");
        devices.add(lampa1);

        lampa2.setName("lampa2");
        lampa2.setStatus(true);
        lampa2.setRoomId("2");
        devices.add(lampa2);

        lampa3.setName("lampa3");
        lampa3.setStatus(true);
        lampa3.setRoomId("3");
//        lampa3.setRoomId(null);
        devices.add(lampa3);

        lampa4.setName("lampa4");
        lampa4.setStatus(true);
        lampa4.setRoomId(pokoj.getId());
        devices.add(lampa4);

//        temp1.setName("temp1");
//        temp1.setStatus(true);
//        temp1.setRoomId(pokoj.getId());
//        temp1.setAdditionalInfo(new TemperatureModel(10.0, 0.0));
//        devices.add(temp1);
//
//        temp2.setName("temp2");
//        temp2.setStatus(true);
//        temp2.setRoomId(sypialnia.getId());
//        temp2.setAdditionalInfo(new TemperatureModel(19.0, 0.0));
//        devices.add(temp2);
//
//        temp3.setName("temp3");
//        temp3.setStatus(true);
//        temp3.setRoomId(kuchnia.getId());
//        temp3.setAdditionalInfo(new TemperatureModel(0.0, 30.0));
//        devices.add(temp3);
//
//        temp4.setName("temp4");
//        temp4.setStatus(true);
//        temp4.setRoomId(pokoj.getId());
//        temp4.setAdditionalInfo(new TemperatureModel(11.0, 0.0));
//        devices.add(temp4);

        return devices;
    }

    public static List<Room> InitRooms(){

        List<Room> rooms = new ArrayList<>();

        Room sypialnia = new Room();
        Room kuchnia = new Room();
        Room pokoj = new Room();


        sypialnia.setName("sypialnia");
        sypialnia.setId("1");
        kuchnia.setName("kuchnia");
        kuchnia.setId("2");
        pokoj.setName("pokoj");
        pokoj.setId("3");

        rooms.add(sypialnia);
        rooms.add(kuchnia);
        rooms.add(pokoj);

        return rooms;
    }
}
