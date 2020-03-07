package pl.edu.pg.student.smarthome.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pl.edu.pg.student.smarthome.DBUtils.DataBaseHelper;
import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.adapters.ProfileAdapter;
import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Profiles;

public class ProfileActivity extends AppCompatActivity {


    private ProfileAdapter pAdapter;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle(R.string.profiles_action_bar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, NewProfileActivity.class));
            }
        });

        DataBaseHelper db = new DataBaseHelper(this);
        Profiles.profiles = new ArrayList<>();
        Profiles.profiles.addAll(db.getAllProfiles());

        notificationManager = NotificationManagerCompat.from(this);

        ListView lv = findViewById(R.id.list);
        pAdapter = new ProfileAdapter(ProfileActivity.this, 0, Profiles.profiles);

        lv.setAdapter(pAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!Profiles.profiles.get(position).getActive()) {
                    Profiles.activeProfile(Profiles.profiles.get(position).getName());
                    setUpNotification();
                } else {
                    Profiles.disactiveProfile(Profiles.profiles.get(position).getName());
                    turnOffNotification();
                }
                pAdapter.notifyDataSetChanged();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Intent editProfileIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                editProfileIntent.putExtra("profilePos", position);
                startActivity(editProfileIntent);

                pAdapter.notifyDataSetChanged();

                return true;
            }
        });
        pAdapter.notifyDataSetChanged();
    }

    private void setUpNotification(){

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_home_black_24dp)
                .setContentTitle("Smarthome")
                .setContentText(new StringBuilder().append(getString(R.string.active_profile)).append(": ").append(Constants.activeProfileName).toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false);

        notificationManager.notify(1, mBuilder.build());
    }

    private void turnOffNotification(){

        notificationManager.cancel(1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pAdapter.notifyDataSetChanged();
    }
}