package com.example.questionanwerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        btn_start = findViewById(R.id.btn_main_start);
        btn_stat = findViewById(R.id.btn_main_stat);

        implementingWaveHeader();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signAnonymouslyWithFirebase();
            }
        });

        btn_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                //startActivity(intent);
                // how many data we've collected so far
            }
        });
    }



    private void signAnonymouslyWithFirebase() {
        if(mCurrentUser == null){
            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast toast = Toast.makeText(MainActivity.this, "Signed in Anonymously!", Toast.LENGTH_LONG);
                        beautifyToast(toast);
                        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Error occurred!" +task.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
        else{
            Toast toast = Toast.makeText(MainActivity.this, "Signed in Anonymously!", Toast.LENGTH_LONG);
            beautifyToast(toast);
            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
            startActivity(intent);
        }

    }

    private void implementingWaveHeader() {
        waveHeader = findViewById(R.id.waveHeader);

        waveHeader.setColorAlpha(.5f);
        waveHeader.setVelocity(5f);
        waveHeader.setProgress(1f);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(50);
    }

    private void beautifyToast(Toast toast){
        View v = toast.getView();
        v.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);

        TextView textView = v.findViewById(android.R.id.message);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        toast.show();
    }
}
