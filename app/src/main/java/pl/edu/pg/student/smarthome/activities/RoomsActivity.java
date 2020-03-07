package pl.edu.pg.student.smarthome.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.pg.student.smarthome.HTTP_Handler.HttpRequestsHandler;
import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.adapters.DeviceAdapter;
import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Device;
import pl.edu.pg.student.smarthome.models.Room;
import pl.edu.pg.student.smarthome.models.TemperatureModel;


public class RoomsActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static Map<String, List<Device>> _devicesInRoom = new HashMap<>();
    private static List<Device> thermometersInRoom = new ArrayList<>();
    private ViewPager mViewPager;
    private static int currentTab;
    private static DeviceAdapter dAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        Boolean once = true;
        int i = 0;
        for (Room room : Constants.rooms){
            List<Device> selected = new ArrayList<>();

            for (Device dev : Constants.devices){
                if(once) {
                    dev.setPositionInList(i++);
                    once = false;
                }
                try {
                    if (dev.getRoomId().equals(room.getId())) {
                        selected.add(dev);

                        if (dev.getTemperatureModel().getTemperature() != 0.0) {
                            thermometersInRoom.add(dev);
                        }
                    }
                }catch (NullPointerException ex){
                    ex.printStackTrace();
                }
            }
            _devicesInRoom.put(room.getName(), selected);
        }

        Device dev = new Device();
        dev.setTemperatureModel(new TemperatureModel(19.0, 0.0));
        thermometersInRoom.add(dev);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.rooms_action_bar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), Constants.rooms.size());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(RoomsActivity.this, NewRoomActivity.class));
                dAdapter.notifyDataSetChanged();
            }
        });
        if(_devicesInRoom.get(Constants.rooms.get(Constants.rooms.size() -1).getName()).isEmpty()) {
            _devicesInRoom.put(Constants.rooms.get(Constants.rooms.size() - 1).getName(), new ArrayList<Device>());
        }
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_rooms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.remove_room) {

            for(Device dev : Constants.devices ){
                if (dev.getRoomId().equals(Constants.rooms.get(currentTab).getId())){
                    dev.setRoomId(null);
                }
            }
            Constants.rooms.remove(currentTab);
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), Constants.rooms.size());

            mViewPager = findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            new DeleteData().execute(Constants.rooms.get(currentTab).getId().toString());
            mSectionsPagerAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), Constants.rooms.size());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            currentTab = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
            View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);
            TextView sectionName = rootView.findViewById(R.id.section_label);
            final String roomName = Constants.rooms.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getName();
            sectionName.setText(roomName);

            List<Device> dd = _devicesInRoom.get(roomName);

            ListView lv = rootView.findViewById(R.id.list);
            if( _devicesInRoom.get(roomName) != null) {
                dAdapter = new DeviceAdapter(getActivity(), 0, _devicesInRoom.get(roomName));
                lv.setAdapter(dAdapter);
            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(_devicesInRoom.get(roomName).get(position).getStatus()){
                        _devicesInRoom.get(roomName).get(position).setStatus(false);
                        Constants.devices.get(_devicesInRoom.get(roomName).get(position).getPositionInList()).setStatus(false);
                    }
                    else {
                        _devicesInRoom.get(roomName).get(position).setStatus(true) ;
                        Constants.devices.get(_devicesInRoom.get(roomName).get(position).getPositionInList()).setStatus(true);
                    }
                    new SendData().execute(Constants.devices.get(_devicesInRoom.get(roomName).get(position).getPositionInList()));
                    dAdapter.notifyDataSetChanged();
                }
            });

            dAdapter.notifyDataSetChanged();
            int temp = 0;
            TextView tempTV = rootView.findViewById(R.id.temp);
            if(thermometersInRoom.get(currentTab).getAdditionalInfo() != null) {
                tempTV.setText((int) thermometersInRoom.get(currentTab).getTemperatureModel().getTemperature());
                tempTV.append("°");
                temp = (int) thermometersInRoom.get(currentTab).getTemperatureModel().getTemperature();
            }
            TextView air = rootView.findViewById(R.id.airClean);
            air.setText(getString(R.string.no_data));

            CircularProgressBar circularProgressBar = rootView.findViewById(R.id.tempCPB);


//            int temp = 10;
            int animationDuration = 0;
            double tempForPB;
            if(temp > 32)
                tempForPB = 100;
            else
                tempForPB = (((double)temp - 16.0) / (32.0 - 16.0)) * 100;
            circularProgressBar.setProgressWithAnimation((float) tempForPB, animationDuration);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        int roomsNumber;

        public SectionsPagerAdapter(FragmentManager fm, int roomsNumber) {
            super(fm);
            this.roomsNumber = roomsNumber;
        }

        @Override
        public Fragment getItem(int position) {

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {

            return this.roomsNumber;
        }
    }

    private static class SendData extends AsyncTask<Device, Void, Void> {

        @Override
        protected Void doInBackground(Device... devices) {

            HttpRequestsHandler.SendPutRequest_Device(devices[0]);

            return null;
        }
    }

    private class DeleteData extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... ids) {

            HttpRequestsHandler.SendDeleteRequest_Room(ids[0]);

            return null;
        }
    }
}
