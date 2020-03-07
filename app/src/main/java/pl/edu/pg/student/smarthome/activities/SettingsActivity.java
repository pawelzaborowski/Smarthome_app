package pl.edu.pg.student.smarthome.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Device;
import pl.edu.pg.student.smarthome.models.Room;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "ManActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle(R.string.settings_action_bar);
        Button updateButton = findViewById(R.id.button);

        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                   new GetData().execute();
                }
        });
    }

    private class GetData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();

            Constants.rooms = new ArrayList<>();
            Constants.devices = new ArrayList<>();

            ResponseEntity<List<Room>> roomsResponse =
                    restTemplate.exchange(Constants.GET_ALL_ROOMS,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Room>>() {
                            });
            Constants.rooms = roomsResponse.getBody();

            ResponseEntity<List<Device>> devResponse =
                    restTemplate.exchange(Constants.GET_ALL_DEVICES,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Device>>() {
                            });
            Constants.devices = devResponse.getBody();


            return null;
        }
    }
}
