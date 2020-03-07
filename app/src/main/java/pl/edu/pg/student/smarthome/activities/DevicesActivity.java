package pl.edu.pg.student.smarthome.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import pl.edu.pg.student.smarthome.HTTP_Handler.HttpRequestsHandler;
import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.adapters.DeviceAdapter;
import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Device;
import pl.edu.pg.student.smarthome.models.Room;

public class DevicesActivity extends AppCompatActivity {


    private SlidingUpPanelLayout mLayout;
    private DeviceAdapter dAdapter;

    private TextView t;
    private EditText devName_value;
    private EditText roomName_value;
    private Spinner status_value;
    private EditText ai_value;
    private int pos;
    private View vi;

    private String selectedItem = " ";
    private String[] spinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        getSupportActionBar().setTitle(R.string.devices_action_bar);

        final ListView lv = findViewById(R.id.list);
        dAdapter = new DeviceAdapter(DevicesActivity.this ,0, Constants.devices);

        devName_value = findViewById(R.id.dev_name_value);
        roomName_value = findViewById(R.id.room_name_value);
        status_value = findViewById(R.id.status_value);
        ai_value = findViewById(R.id.ip_value);
        Button acceptButton = findViewById(R.id.accept);

        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_positions, android.R.layout.simple_spinner_item);
         spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_value.setAdapter(spinnerAdapter);
        spinnerArray = getResources().getStringArray(R.array.status_positions);

        lv.setAdapter(dAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                vi = view;
//                t.setText(new StringBuilder().append(getString(R.string.properties_capital_letter)).append(' ').append(Constants.devices.get(position).getName()).toString());
                int start = getString(R.string.properties_capital_letter).length() + 1;
                int end = 0;
//                if(Constants.devices.get(position).getName().length() > 0)
//                     end = start + Constants.devices.get(position).getName().length();
                Spannable s = (Spannable)t.getText();
//                s.setSpan(new ForegroundColorSpan(0xFFFF4081), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                t.setTextSize(16);

                devName_value.setText(Constants.devices.get(position).getName());
                roomName_value.setText(Constants.roomsNameId.get(Constants.devices.get(position).getRoomId()));
//                if( Constants.devices.get(position).getTemperatureModel().getDistance() != 0.0) {
//                    ai_value.setText((int) Constants.devices.get(position).getTemperatureModel().getDistance());
//                }
//                else{
//                    ai_value.setText((int) Constants.devices.get(position).getTemperatureModel().getDistance());
//                }

                if(Constants.devices.get(position).getStatus())
                    status_value.setSelection(0);
                else
                    status_value.setSelection(1);

                status_value.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedItem = spinnerArray[position];
                        if(selectedItem.equals("On")){
                            new TurnOnDevice().execute(Constants.devices.get(pos).getId());
                        }
                        else if(selectedItem.equals("Off")){
                            new TurnOffDevice().execute(Constants.devices.get(pos).getId());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                dAdapter.notifyDataSetChanged();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Constants.devices.get(pos).setName(devName_value.getText().toString());

                for(Room r : Constants.rooms){
                    if(r.getName().equals(roomName_value.getText().toString())){
                        Constants.devices.get(pos).setRoomId(r.getId());
                        break;
                    }
                    if(roomName_value.getText().toString().equals("")){
                        Constants.devices.get(pos).setRoomId(null);
                        break;
                    }
                }
                Constants.devices.get(pos).setIp(ai_value.getText().toString());

                if (selectedItem.equals(getResources().getString(R.string.on))){
                    Constants.devices.get(pos).setStatus(true);
//                    new TurnOnDevice().execute(Constants.devices.get(pos).getId());
                }
                else if(selectedItem.equals(getResources().getString(R.string.off))) {
                    Constants.devices.get(pos).setStatus(false);
//                    new TurnOffDevice().execute(Constants.devices.get(pos).getId());
                }

                if(Constants.devices.get(pos).getRoomId() == null) {
                    dAdapter.getView(pos, vi, null).setBackgroundColor(0xFFF7AEAE);
                }
                else {
                    dAdapter.getView(pos, vi, null).setBackgroundColor(0xFFFFFF);
                }

                dAdapter.notifyDataSetChanged();
                new SendData().execute(Constants.devices.get(pos));
                onBackPressed();
            }
        });

        dAdapter.notifyDataSetChanged();


        mLayout = findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        t = findViewById(R.id.prop);
        t.setText(getString(R.string.properties_capital_letter), TextView.BufferType.SPANNABLE);
        t.setTextSize(16);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            dAdapter.notifyDataSetChanged();

        } else {
            super.onBackPressed();
            dAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_device, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.add_new_device){
            startActivity(new Intent(DevicesActivity.this, NewDeviceActivity.class));
            dAdapter.notifyDataSetChanged();
        }

        dAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    private class SendData extends AsyncTask<Device, Void, Void> {

        @Override
        protected Void doInBackground(Device... devices) {

            HttpRequestsHandler.SendPutRequest_Device(devices[0]);

            return null;
        }
    }

    private class TurnOnDevice extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... ids) {

            HttpRequestsHandler.TurnOnDevice(ids[0]);

            return null;
        }
    }

    private class TurnOffDevice extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... ids) {

            HttpRequestsHandler.TurnOffDevice(ids[0]);

            return null;
        }
    }
}

