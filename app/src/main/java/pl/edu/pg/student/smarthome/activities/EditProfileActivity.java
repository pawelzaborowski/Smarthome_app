package pl.edu.pg.student.smarthome.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import pl.edu.pg.student.smarthome.DBUtils.DataBaseHelper;
import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.adapters.DeviceAdapter;
import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Profiles;

public class EditProfileActivity extends AppCompatActivity {

    private static String m_val;
    private EditText editTextName;
    private EditText input;
    private int pos;
    private DataBaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle(R.string.edit_profile_action_bar);

        db = new DataBaseHelper(this);
        pos = getIntent().getIntExtra("profilePos", 0);
        editTextName = findViewById(R.id.name);
        editTextName.setText(Profiles.profiles.get(pos).getName());

        ListView lv = findViewById(R.id.list);
        final DeviceAdapter dAdapter;
        if(Profiles.profiles.get(pos).getDevices() != null) {
            dAdapter = new DeviceAdapter(EditProfileActivity.this, 0, Profiles.profiles.get(pos).getDevices());
        }else{
            dAdapter = null;
        }

        lv.setAdapter(dAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(Profiles.profiles.get(pos).getDevices().get(position).getStatus()){
                    if(Constants.devices.contains(Profiles.profiles.get(pos).getDevices().get(position))){
                        Constants.devices.get(Constants.devices.indexOf(Profiles.profiles.get(pos).getDevices().get(position))).setStatus(false);
                    }
                }
                else{
                    if(Constants.devices.contains(Profiles.profiles.get(pos).getDevices().get(position))){
                        Constants.devices.get(Constants.devices.indexOf(Profiles.profiles.get(pos).getDevices().get(position))).setStatus(true);
                    }
                }
                dAdapter.notifyDataSetChanged();
//                if(Profiles.profiles.get(pos).getDevices().get(position).getStatus()){
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
//                    builder.setTitle("enter value");
//                    input = new EditText(EditProfileActivity.this);
//                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
//                    builder.setView(input);
//
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            m_val = input.getText().toString();
//                        }
//                    });
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    builder.show();
//                }
            }
        });

        dAdapter.notifyDataSetChanged();

        Button addButton = findViewById(R.id.accept);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Profiles.profiles.get(pos).setName(editTextName.getText().toString());
                db.updateProfile(Profiles.profiles.get(pos));
                finish();
            }
        });

        Button removeButton = findViewById(R.id.remove);
        removeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db.deleteProfile(Profiles.profiles.get(pos));
                Profiles.profiles.remove(pos);
                finish();
            }
        });
    }
}
