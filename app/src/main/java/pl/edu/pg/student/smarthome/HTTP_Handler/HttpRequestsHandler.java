package pl.edu.pg.student.smarthome.HTTP_Handler;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Device;
import pl.edu.pg.student.smarthome.models.Room;

/**
 * Created by Pawel on 24.09.2018.
 */

public class HttpRequestsHandler {

    public static void TurnOnDevice(String id){

        RestTemplate restTemplate = new RestTemplate();
        String url = Constants.TURN_DEVICE_ON_BY_ID + id;
        restTemplate.getForObject(url, String.class);
    }

    public static void TurnOffDevice(String id){

        RestTemplate restTemplate = new RestTemplate();
        String url = Constants.TURN_DEVICE_OFF_BY_ID + id;
        restTemplate.getForObject(url, String.class);
    }

    public static void SendPostRequest_Device(Device dev){

        RestTemplate restTemplate = new RestTemplate();

        String url = Constants.POST_NEW_DEVICE_1 + dev.getName() + Constants.POST_NEW_DEVICE_2;// + dev.getStatus().toString();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.postForObject(url, dev, ResponseEntity.class);
    }

    public static void SendPostRequestUsingGet_Device(String name, Boolean status){

        RestTemplate restTemplate = new RestTemplate();

        String requestJson = "";
        String url = Constants.POST_NEW_DEVICE_1 + name + Constants.POST_NEW_DEVICE_2 + status.toString();

        ResponseEntity<List<Room>> roomsResponse =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Room>>() {
                        });
    }

    public static void SendPutRequest_Device(Device dev){

        RestTemplate restTemplate = new RestTemplate();

        String url = Constants.PUT_DEVICE_CHANGE + dev.getId();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.put(url, dev, ResponseEntity.class);
    }

    public static void SendDeleteRequest_Device(String id){

        RestTemplate restTemplate = new RestTemplate();

        String url = Constants.DELETE_DEVICE_BY_ID + id;
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        restTemplate.delete(url);

    }

    public static void SendPostRequest_Room(Room room){

        RestTemplate restTemplate = new RestTemplate();

        String url = Constants.POST_NEW_ROOM + room.getName();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        restTemplate.postForObject(url, room, ResponseEntity.class);
    }

    public static void SendPutRequest_Room(Room room){

        RestTemplate restTemplate = new RestTemplate();

        String url = Constants.PUT_ROOM_CHANGE + room.getId();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        restTemplate.put(url, room, ResponseEntity.class);
    }

    public static void SendDeleteRequest_Room(String id){

        RestTemplate restTemplate = new RestTemplate();

        String url = Constants.DELETE_ROOM_BY_ID + id;
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        restTemplate.delete(url);
    }

}

//https://docs.spring.io/spring-android/docs/current/reference/html/rest-template.html#rest-template-methods