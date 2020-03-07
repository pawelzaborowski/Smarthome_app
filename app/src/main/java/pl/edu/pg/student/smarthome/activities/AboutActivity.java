package pl.edu.pg.student.smarthome.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import pl.edu.pg.student.smarthome.R;

public class AboutActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle(R.string.about_action_bar);
        TextView aboutTextView = findViewById(R.id.about);

        aboutTextView.setText("Dziękujemy za skorzystanie z produktu, który jest owocem pracy" +
                "inżynierskiej zespołu studentów Politechniki Gdańskiej w składzie:\n" +
                "\n - Adam Wawrzynski" +
                "\n - Michał Wiliński" +
                "\n - Paweł Zaborowski");
    }
}
