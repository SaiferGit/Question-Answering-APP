package com.example.questionanwerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.scwang.wave.MultiWaveHeader;

public class MainActivity extends AppCompatActivity {

    MultiWaveHeader waveHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waveHeader = findViewById(R.id.waveHeader);

        waveHeader.setColorAlpha(.5f);
        waveHeader.setVelocity(5f);
        waveHeader.setProgress(1f);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(50);
    }
}
