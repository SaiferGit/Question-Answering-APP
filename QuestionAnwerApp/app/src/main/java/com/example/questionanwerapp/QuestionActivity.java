package com.example.questionanwerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    Button answer1, answer2, answer3, answer4, next, previous;
    TextView questionView, counterView;
    EditText answerText;
    LinearLayout optionsContainer;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = findViewById(R.id.question_toolbar);
        setSupportActionBar(toolbar);
        initializingAll();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                playAnimation(questionView, 0);
            }
        });
    }

    private void initializingAll() {
        answer1 = findViewById(R.id.answer_button1);
        answer2 = findViewById(R.id.answer_button2);
        answer3 = findViewById(R.id.answer_button3);
        answer4 = findViewById(R.id.answer_button4);
        next = findViewById(R.id.button6);
        previous = findViewById(R.id.button5);
        questionView = findViewById(R.id.question_textView);
        counterView = findViewById(R.id.question_countView);
        answerText = findViewById(R.id.answer_editText);
        optionsContainer = findViewById(R.id.upper_linearLayout2);
    }

    //adding animations before loading questions
    private void playAnimation(final View view, final int value){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
        .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(value == 0 && count < 5){
                    playAnimation(optionsContainer.getChildAt(count),0);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(value == 0){
                    playAnimation(view, 1);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
