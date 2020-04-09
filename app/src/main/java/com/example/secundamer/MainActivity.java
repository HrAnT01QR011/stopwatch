package com.example.secundamer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    int btn_situation = 1;
    List<String> history_list = new ArrayList<>();
    long startMilis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.start_stop_resume_btn);
        btn.setOnClickListener(this);
        final Intent intent = new Intent(this, SavedTimeActivity.class);

        findViewById(R.id.open_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] hist = new String[history_list.size()];

                for (int i = 0; i < hist.length; i++) {
                    hist[i] = history_list.get(i);
                }

                intent.putExtra("history", hist);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {

        final Button save_btn = findViewById(R.id.save_btn);
        final TextView stopwatch = findViewById(R.id.stopwatch);
        final StringBuilder str = new StringBuilder();

        startMilis = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (btn_situation == 2) {
                    str.delete(0, str.capacity());

                    long current = System.currentTimeMillis();
                    final long finalMilis = current - startMilis;
                    long finalOnlyMilis = finalMilis % 1000;
                    long finalOnlySeconds = (finalMilis / 1000) % 60;
                    long finalOnlyMinutes = (finalMilis / 1000) / 60;


                    if (finalOnlyMinutes < 10) {
                        str.append("0");
                    }
                    str.append(finalOnlyMinutes);


                    str.append(":");

                    if (finalOnlySeconds < 10) {
                        str.append("0");
                    }
                    str.append(finalOnlySeconds);

                    str.append(":");


                    if (finalOnlyMilis < 10) {
                        str.append("00");
                    } else if (finalOnlyMilis < 100) {
                        str.append("0");
                    }
                    str.append(finalOnlyMilis);

                    try {
                        Thread.sleep(10);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                stopwatch.setText(str);

                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn_situation++;

        if (btn_situation == 1) {
            btn.setText("Start");
            stopwatch.setText("00:00:000");
            save_btn.setVisibility(GONE);
        } else if (btn_situation == 2) {
            btn.setText("Stop");
            save_btn.setVisibility(GONE);
            thread.start();
        } else {
            btn.setText("Resume");
            save_btn.setVisibility(VISIBLE);
            btn_situation = 0;



            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stopwatch_text = (String) stopwatch.getText();
                    history_list.add(stopwatch_text);
                    save_btn.setVisibility(GONE);
                }
            });

        }
    }
}
