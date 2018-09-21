package pl.edu.pg.student.smarthome.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

import pl.edu.pg.student.smarthome.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int roomsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initDataFromService();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //todo locals
//        if (id == R.id.choose_polish_language){
//            setLocale("pl");
//        }
//        if (id == R.id.choose_english_language){
//            setLocale("en");
//        }
//        if (id == R.id.refresh){
//            new GetData().execute();
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rooms) {
            Intent roomsActivityIntent = new Intent(this, RoomsActivity.class);
            roomsActivityIntent.putExtra("roomsNumber", roomsNumber);
            startActivity(roomsActivityIntent);

        } else if (id == R.id.nav_devices) {
            Intent devicesActivityIntent = new Intent(this, DevicesActivity.class);
            startActivity(devicesActivityIntent);

        } else if (id == R.id.nav_profiles) {

        } else if (id == R.id.nav_stats) {
            Intent statsActivityIntent = new Intent(this, StatsActivity.class);
            startActivity(statsActivityIntent);

        } else if (id == R.id.nav_settings) {
            Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivityIntent);

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initDataFromService(){

        //todo move to .properties
        String url = "";

//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//        String result = restTemplate.getForObject(url, String.class, "foo");

//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
//        map.add("email", "first.last@example.com");
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );

        roomsNumber = 7;
    }

    public void setLocale(String lang){

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}
