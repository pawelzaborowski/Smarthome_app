package pl.edu.pg.student.smarthome.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import pl.edu.pg.student.smarthome.Bootstrap;
import pl.edu.pg.student.smarthome.R;
import pl.edu.pg.student.smarthome.models.Constants;
import pl.edu.pg.student.smarthome.models.Device;
import pl.edu.pg.student.smarthome.models.Profiles;
import pl.edu.pg.student.smarthome.models.Room;
import pl.edu.pg.student.smarthome.models.WeatherClouds;
import pl.edu.pg.student.smarthome.models.WeatherDesc;
import pl.edu.pg.student.smarthome.models.WeatherModel;
import pl.edu.pg.student.smarthome.models.WeatherTemp;
import pl.edu.pg.student.smarthome.models.WeatherWind;
import pl.edu.pg.student.smarthome.wrapper.LocaleHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private WeatherModel weather;

    private ToggleButton buttonIn;
    private ToggleButton buttonOut;
    private ToggleButton buttonNight;
    private ImageView weatherIcon;
    private TextView activeProfileTextBox;
    private TextView temperatureTextView;
    private TextView humidityTextView;
    private TextView descTextView;
    private TextView windTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new GetWeather().execute();
//        new GetData().execute();

        temperatureTextView = findViewById(R.id.temp);
        humidityTextView = findViewById(R.id.humidity_value);
        descTextView = findViewById(R.id.desc);
        weatherIcon = findViewById(R.id.icon);
        windTextView = findViewById(R.id.wind_value);

        Context mContext = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        buttonIn = findViewById(R.id.button_in);
        buttonIn.setOnClickListener(this);

        buttonOut = findViewById(R.id.button_out);
        buttonOut.setOnClickListener(this);

        buttonNight = findViewById(R.id.button_night);
        buttonNight.setOnClickListener(this);

        activeProfileTextBox = findViewById(R.id.act_profile_value);

//        Constants.rooms = Bootstrap.InitRooms();
//        Constants.devices = Bootstrap.InitDevices();
        if(Constants.rooms != null) {
            for (Room r : Constants.rooms) {
                Constants.roomsNameId.put(r.getId(), r.getName());
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        if (id == R.id.choose_polish_language){
            updateLang("pl");
            this.recreate();
        }
        if (id == R.id.choose_english_language){
            updateLang("en");
            this.recreate();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateLang(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        setTitle(resources.getString(R.string.active_profile));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_rooms) {
            Intent roomsActivityIntent = new Intent(this, RoomsActivity.class);
            startActivity(roomsActivityIntent);

        } else if (id == R.id.nav_devices) {
            Intent devicesActivityIntent = new Intent(this, DevicesActivity.class);
            startActivity(devicesActivityIntent);

        } else if (id == R.id.nav_profiles) {
            Intent profileIntent = new Intent(this, ProfileActivity.class);
            startActivity(profileIntent);

        } else if (id == R.id.nav_stats) {
//            Intent statsActivityIntent = new Intent(this, StatsActivity.class);
//            startActivity(statsActivityIntent);

        } else if (id == R.id.nav_settings) {
            Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivityIntent);

        } else if (id == R.id.nav_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.button_in:
                Profiles.activeProfile("In");
                buttonNight.setChecked(false);
                buttonOut.setChecked(false);
                break;

            case R.id.button_out:
                Profiles.activeProfile("Out");
                buttonIn.setChecked(false);
                buttonNight.setChecked(false);
                break;

            case R.id.button_night:
                Profiles.activeProfile("Night");
                buttonIn.setChecked(false);
                buttonOut.setChecked(false);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (Constants.ifActiveProfile) {
            activeProfileTextBox.setText(Constants.activeProfileName);
            activeProfileTextBox.setTextColor(0xFF669900);
        } else {
            activeProfileTextBox.setText(getString(R.string.none));
            activeProfileTextBox.setTextColor(0xFFCC0000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Constants.ifActiveProfile){
            activeProfileTextBox.setText(Constants.activeProfileName);
            activeProfileTextBox.setTextColor(0xFF669900);
        }
        else {
            activeProfileTextBox.setText(getString(R.string.none));
            activeProfileTextBox.setTextColor(0xFFCC0000);
        }
    }

    class GetWeather extends AsyncTask<Void, Void, WeatherModel> {

        Bitmap bm;
        Spannable s;

        @Override
        protected WeatherModel doInBackground(Void... arg0) {

            WeatherModel wm;
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<WeatherModel> weatherResponse =
                    restTemplate.exchange("http://api.openweathermap.org/data/2.5/weather?q=gdansk,pl&APPID=" + Constants.OPENWEATHERMAPAPI_KEY,
                            HttpMethod.GET, null, WeatherModel.class);
            wm = weatherResponse.getBody();
            weather = wm;

            byte[] img;
            img = imageByter(getIconUrl());
            if(img != null) {
                bm = BitmapFactory.decodeByteArray(img, 0, img.length);
            }

            return wm;
        }

        @Override
        protected void onPostExecute(WeatherModel wm){
            super.onPostExecute(wm);
            WeatherTemp tempP = weather.getMain();
            int temp = (int) (tempP.getTemp() - 273.15);
            WeatherClouds cl = weather.getClouds();
            WeatherDesc[] d = weather.getWeather();
            WeatherWind w = weather.getWind();
            String wdesc = d[0].getDescription();
            temperatureTextView.setText(String.valueOf(temp));
            temperatureTextView.append("°");
            humidityTextView.setText(String.valueOf(tempP.getHumidity()));
            humidityTextView.append(" %");

            descTextView.setText(" ", TextView.BufferType.SPANNABLE);
            descTextView.setText(new StringBuilder().append(getString(R.string.weather_today)).append(" ").append(d[0].getDescription()));
            int start = getString(R.string.weather_today).length() + 1;
            int end = start + d[0].getDescription().length();
            s = (Spannable)descTextView.getText();
            s.setSpan(new ForegroundColorSpan(0xFF000000), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            windTextView.setText(String.valueOf(w.getSpeed()));
            windTextView.append(" m/s");
            weatherIcon.setImageBitmap(bm);
        }

        private String getIconUrl() {

            String url;
            WeatherDesc w = weather.getWeather()[0];
            // 01
            if (w.getId() == 800) {
                url = Constants.icon_clear_sky_day;

            // 02
            } else if (w.getId() == 801) {
                url = Constants.icon_few_clouds_day;

            // 03
            } else if (w.getId() == 802) {
                url = Constants.icon_scattered_clouds_day;

            // 04
            } else if ( w.getId() == 803 || w.getId() == 804) {
                url = Constants.icon_broken_clouds_day;

            // 09
            } else if (w.getId() == 520 || w.getId() ==  521 || w.getId() == 522 || w.getId() == 531 || w.getId() == 300 ||
                    w.getId() == 301 || w.getId() == 302 || w.getId() == 310 || w.getId() == 311 || w.getId() == 312 || w.getId() == 313 || w.getId() == 314 || w.getId() == 321) {
                url = Constants.icon_shower_rain_day;

            // 10
            } else if (w.getId() == 500 || w.getId() == 501 || w.getId() == 502 || w.getId() == 503 || w.getId() == 504) {
                url = Constants.icon_rain_day;

            // 11
            } else if (w.getId() == 200 || w.getId() == 201 || w.getId() == 202 || w.getId() == 210 || w.getId() == 211 ||
                    w.getId() == 212 || w.getId() == 221 || w.getId() == 230 || w.getId() == 232) {
                url = Constants.icon_thunderstorm_day;

            // 13
            } else if (w.getId() == 511 ||  w.getId() == 600 || w.getId() == 601 || w.getId() == 602 || w.getId() == 611 ||
                    w.getId() == 612 || w.getId() == 615 || w.getId() == 616 || w.getId() == 620 || w.getId() == 621 || w.getId() == 622) {
                url = Constants.icon_snow_day;

            // 50
            } else if (w.getId() == 701 || w.getId() == 711 || w.getId() == 721 || w.getId() == 731 || w.getId() == 741 ||
                    w.getId() == 751 || w.getId() == 761 || w.getId() == 762 || w.getId() == 771 || w.getId() == 781) {
                url = Constants.icon_mist_day;

            } else {
                url = "";
            }

            return url;
        }
        private byte[] imageByter(String _url) {
            try {
                URL url = new URL(_url);
                InputStream is = (InputStream) url.getContent();
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((bytesRead = is.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                return output.toByteArray();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private class GetData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            RestTemplate restTemplate = new RestTemplate();

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
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//          /  Constants.rooms = restTemplate.getForObject(Constants.GET_ALL_ROOMS, ParameterizedTypeReference<List<Room>>.class);
            return null;
        }
    }
}
