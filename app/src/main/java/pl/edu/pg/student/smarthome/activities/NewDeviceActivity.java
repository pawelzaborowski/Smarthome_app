package pl.edu.pg.student.smarthome.activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.util.Map;
import java.util.UUID;

import pl.edu.pg.student.smarthome.HTTP_Handler.HttpRequestsHandler;
import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Device;

public class NewDeviceActivity extends AppCompatActivity {

    private EditText name_value;
    private EditText room_name_value;
    private EditText ip_value;
    private Boolean correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device);

        getSupportActionBar().setTitle(R.string.new_device_action_bar);

        Button addButton = findViewById(R.id.add);
        name_value = findViewById(R.id.dev_name_value);
        room_name_value = findViewById(R.id.room_name_value);
        ip_value = findViewById(R.id.ip_value);

        correct = true;

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Device dev = null;
                dev = new Device();
                dev.setName(name_value.getText().toString());

                for (Map.Entry<String, String> entry : Constants.roomsNameId.entrySet()) {
                    if (room_name_value.getText().toString().equals(entry.getValue())) {
                        dev.setRoomId(entry.getKey());
                        correct = true;
                        break;
                    } else {
                        correct = false;
                    }
                }

                if (!correct) {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(NewDeviceActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(NewDeviceActivity.this);
                    }
                    builder.setTitle(getString(R.string.no_such_room))
                            .setMessage(getString(R.string.want_continue) + '?')
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    Device _dev = null;
                                    _dev = new Device();
                                    _dev.setName(name_value.getText().toString());
                                    _dev.setIp(ip_value.getText().toString());
                                    _dev.setStatus(true);
                                    Constants.devices.add(_dev);

                                    new SendData().execute(_dev);
                                    finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

                if (correct) {
                    dev.setIp(ip_value.getText().toString());
                    dev.setStatus(true);
                    Constants.devices.add(dev);
                    new SendData().execute(dev);

                    finish();
                }
            }
        });
    }

    private class SendData extends AsyncTask<Device, Void, Void> {

        @Override
        protected Void doInBackground(Device... devices) {

            HttpRequestsHandler.SendPostRequest_Device(devices[0]);

            return null;
        }
    }
}