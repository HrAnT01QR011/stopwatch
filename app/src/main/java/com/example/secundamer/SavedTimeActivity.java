package com.example.secundamer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SavedTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        Intent intent = getIntent();

        LinearLayout historyContainer = findViewById(R.id.history_container);

        String[] histories = intent.getStringArrayExtra("history");

        for (String history : histories) {
            TextView textView = new TextView(this);
            textView.setTextSize(40);

            textView.setText(history);
            historyContainer.addView(textView);
        }
    }
}
