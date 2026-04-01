package com.example.questionanwerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.scwang.wave.MultiWaveHeader;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    MultiWaveHeader waveHeader;
    Button btn_start, btn_stat;
    AnimationDrawable animationDrawable;
    ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mAuth = FirebaseAuth.getInstance();
            mCurrentUser = mAuth.getCurrentUser();
        } catch (Exception e) {
            Toast.makeText(this, "Firebase init failed", Toast.LENGTH_LONG).show();
        }

        btn_start = findViewById(R.id.btn_main_start);
        btn_stat = findViewById(R.id.btn_main_stat);

        loading = findViewById(R.id.main_progressBar);
        loading.setVisibility(View.GONE);

        if (loading.getDrawable() instanceof AnimationDrawable) {
            animationDrawable = (AnimationDrawable) loading.getDrawable();
        }

        implementingWaveHeader();

        btn_start.setOnClickListener(view -> {
            if (animationDrawable != null) {
                loading.setVisibility(View.VISIBLE);
                animationDrawable.start();
            }

            signAnonymouslyWithFirebase();
        });

        btn_stat.setOnClickListener(view -> {
            Toast.makeText(this, "Stats feature coming soon", Toast.LENGTH_SHORT).show();
        });
    }

    private void signAnonymouslyWithFirebase() {

        // 🔥 TEMPORARY FIX: skip Firebase to confirm crash source
        if (mAuth == null) {
            goToNextScreen();
            return;
        }

        if (mCurrentUser == null) {
            mAuth.signInAnonymously().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    goToNextScreen();
                } else {
                    Toast.makeText(this, "Firebase Error", Toast.LENGTH_LONG).show();
                    stopLoading();
                }
            });
        } else {
            goToNextScreen();
        }
    }

    private void goToNextScreen() {
        Toast.makeText(this, "Proceeding...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, QuestionActivity.class));
        stopLoading();
    }

    private void stopLoading() {
        if (animationDrawable != null) animationDrawable.stop();
        if (loading != null) loading.setVisibility(View.GONE);
    }

    private void implementingWaveHeader() {
        waveHeader = findViewById(R.id.waveHeader);

        if (waveHeader != null) {
            waveHeader.setColorAlpha(.5f);
            waveHeader.setVelocity(5f);
            waveHeader.setProgress(1f);
            waveHeader.setGradientAngle(45);
            waveHeader.setWaveHeight(50);
        }
    }
}
