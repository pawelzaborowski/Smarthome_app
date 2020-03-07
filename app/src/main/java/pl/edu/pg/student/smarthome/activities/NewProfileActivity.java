package pl.edu.pg.student.smarthome.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pg.student.smarthome.DBUtils.DataBaseHelper;
import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.adapters.PairAdapter;
import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Device;
import pl.edu.pg.student.smarthome.models.Profile;
import pl.edu.pg.student.smarthome.models.Profiles;

public class NewProfileActivity extends AppCompatActivity {

    private List<Device> selected = new ArrayList<>();
    private List<Pair<Device, Boolean>> pairs = new ArrayList<>();
    private EditText nameEditText;
    private PairAdapter dAdapter;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        getSupportActionBar().setTitle(R.string.new_profile_action_bar);
        db = new DataBaseHelper(this);

        for(Device dev : Constants.devices){
            pairs.add(new Pair<>(dev, false));
        }

        nameEditText = findViewById(R.id.name);

        ListView lv = findViewById(R.id.list);
        dAdapter = new PairAdapter(NewProfileActivity.this ,0, pairs);

        lv.setAdapter(dAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(pairs.get(position).second){
                    pairs.set(position, new Pair<>(pairs.get(position).first, false));

                    try{
                        selected.remove(Constants.devices.get(position));
                    }catch (NullPointerException e){
                        throw e;
                    }
                }
                else {
                    pairs.set(position, new Pair<>(pairs.get(position).first, true));
                    selected.add(Constants.devices.get(position));

                }
                dAdapter.notifyDataSetChanged();
            }
        });
        dAdapter.notifyDataSetChanged();


        Button acceptButton = findViewById(R.id.add);
        acceptButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Profile p = new Profile();
                p.setName(nameEditText.getText().toString());
                p.setDevices(selected);
                Profiles.profiles.add(p);
                long id = db.insertProfile(p);
                finish();
            }
        });
    }
}

