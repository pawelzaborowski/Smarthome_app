package pl.edu.pg.student.smarthome.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.springframework.web.client.RestTemplate;

import java.util.Locale;

import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.models.WeatherModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int roomsNumber;
    private String weather_url = "http://api.openweathermap.org/data/2.5/weather?q=gdansk,pl&APPID=";
    private String smarthomeApi_url;
    private TextView textView;
    protected WeatherModel weather;
    private String TAG = MainActivity.class.getSimpleName();

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

//        select image here based on description
        new GetWeather().execute();

//        WeatherTemp tempP = this.weather.getMain();
//        WeatherClouds cl = this.weather.getClouds();
//        WeatherDesc[] d = this.weather.getWeather();
//        WeatherWind w = this.weather.getWind();
//        textView.setText(String.valueOf(tempP.getTemp()));
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

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

//        todo translate
        if (id == R.id.choose_polish_language){
            setLocale("pl");
        }
        if (id == R.id.choose_english_language){
            setLocale("en");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

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

        Locale newLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = newLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    public class GetWeather extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            RestTemplate restTemplate = new RestTemplate();
            weather = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q=gdansk,pl&APPID=c052d034d955b18294cfa232c0975a78", WeatherModel.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }
}
