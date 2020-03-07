package pl.edu.pg.student.smarthome.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.edu.pg.student.smarthome.HTTP_Handler.HttpRequestsHandler;
import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Room;

public class NewRoomActivity extends AppCompatActivity {

    private EditText room_name_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);

        getSupportActionBar().setTitle(R.string.new_room_action_bar);

        room_name_value = findViewById(R.id.room_name_value);
        Button addButton = findViewById(R.id.add);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Room room = new Room();
                room.setName(room_name_value.getText().toString());
                Constants.rooms.add(room);
                new SendData().execute(room);
                finish();
            }
        });
    }

    private class SendData extends AsyncTask<Room, Void, Void> {

        @Override
        protected Void doInBackground(Room... rooms) {

            HttpRequestsHandler.SendPostRequest_Room(rooms[0]);
            return null;
        }
    }
}
