package com.example.questionanwerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.wave.MultiWaveHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {

    Button answer1, answer2, answer3, answer4, next, previous;
    TextView questionView, counterView;
    EditText answerText, genText, holiText, strText;
    LinearLayout optionsContainer, writboxContainer, avgContainer;
    List<QuestionModel> list;
    MultiWaveHeader waveHeader;

    private int position = 0;

    private int count = 0;

    private String[] bgList = {"#CF9A41", "#EE9198", "#FFAD00", "#6C3C26", "#C7A338", "#7E412E", "#522B1A", "#2C112A",
                                "#044660", "#08B3AB", "#000000", "#BB5769", "#00496A"," #776CB2", "#CCBCA5", "#31273B",
                                "#CDD6D5", "#33444E", "#A33A47", "#050B2B", "#495B53", "#002540"};

    private String[] btnList = {"#048D79", "#F4B504", "#0B1F33", "#290000", "#58221C", "#D67B80", "#8E3463", "#844257", "#013554",
                                "#EA4136", "#98D3E1", "#667FB5", "#79963C", "#212123", "#000000", "#5A4692", "#142E54"};

    private int randIntBG = 0, randIntBtn = 0;
    Random random = new Random();
    Toolbar toolbar;
    private boolean bool = false, avgBool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        implementingWaveHeader();


        initializingAll();


        list = new ArrayList<>();
        addingQuestions();
        //list.add();

        for(int i = 0; i < 4; i++){
            optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getAnswer((Button) view);
                }
            });
        }

        // by default 1st q load thakbe
        playAnimation(questionView, 0, list.get(position).getQuestion());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                implementingWaveHeader();
                changingColorDynamically();

                // checking for write box
                if(bool){
                    optionsContainer.setVisibility(View.GONE);
                    writboxContainer.setVisibility(View.VISIBLE);

                    String editText = answerText.getText().toString();

                    // if the fields are empty
                    if(TextUtils.isEmpty(editText) ){
                        answerText.requestFocus();
                        answerText.setError("Hey! you forgot to type your answer");
                        return;
                    }
                    else{
                        Toast toast = Toast.makeText(QuestionActivity.this, "Ans: " +editText, Toast.LENGTH_SHORT);
                        beautifyToast(toast);
                    }
                }

                // checking for avg box
                if(avgBool == true){

                    optionsContainer.setVisibility(View.GONE);
                    writboxContainer.setVisibility(View.GONE);
                    String editText1 = holiText.getText().toString();
                    String editText2 = genText.getText().toString();
                    String editText3 = strText.getText().toString();

                    // if the fields are empty
                    if(TextUtils.isEmpty(editText1) ){
                        holiText.requestFocus();
                        holiText.setError("Hey! you forgot to type here");
                        return;
                    }

                    // if the fields are empty
                    if(TextUtils.isEmpty(editText2) ){
                        genText.requestFocus();
                        genText.setError("Hey! you forgot to type here");
                        return;
                    }

                    // if the fields are empty
                    if(TextUtils.isEmpty(editText3) ){
                        strText.requestFocus();
                        strText.setError("Hey! you forgot to type here");
                        return;
                    }
                    else{
                        Toast toast1 = Toast.makeText(QuestionActivity.this, "Ans1: " +editText1, Toast.LENGTH_SHORT);
                        beautifyToast(toast1);
                        Toast toast2 = Toast.makeText(QuestionActivity.this, "Ans2: " +editText2, Toast.LENGTH_SHORT);
                        beautifyToast(toast2);
                        Toast toast3 = Toast.makeText(QuestionActivity.this, "Ans3: " +editText3, Toast.LENGTH_SHORT);
                        beautifyToast(toast3);

                    }
                }



                if(answer4.getVisibility() == View.GONE ){
                    answer4.setVisibility(View.VISIBLE);
                }

                enableOption(true);
                if(position == list.size()){
                    Toast toast = Toast.makeText(QuestionActivity.this, "Thank you for your Data", Toast.LENGTH_SHORT);
                    beautifyToast(toast);
                    Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    position++;
                    count = 0;
                    if(position < 4){
                        next.setEnabled(false);
                        next.setAlpha(0.5f);
                        playAnimation(questionView, 0, list.get(position).getQuestion());
                    }
                    else if(position < 12){
                        //counterView.setText(position+1 + "/" + list.size());
                        //answerText.getText().clear();
                        //questionView.setText(list.get(position).getQuestion());
                        try {
                            playAnimationForWriteContainer(questionView, 0, list.get(position).getQuestion());
                        }catch (Exception e){
                            Log.d("SAIF", "Exception:", e);
                        }
                        optionsContainer.setVisibility(View.GONE);
                        writboxContainer.setVisibility(View.VISIBLE);
                        next.setEnabled(true);
                        next.setAlpha(1f);
                        bool = true;
                    }
                    else{
                        bool = false;
                        avgBool = true;
                        genText.getText().clear();
                        holiText.getText().clear();
                        strText.getText().clear();
                        try {
                            playAnimationForAvgContainer(questionView, 0, list.get(position).getQuestion());
                        }catch (Exception e){
                            Log.d("SAIF", "Exception:", e);
                        }
                        optionsContainer.setVisibility(View.GONE);
                        writboxContainer.setVisibility(View.GONE);
                        avgContainer.setVisibility(View.VISIBLE);
                        next.setEnabled(true);
                        next.setAlpha(1f);
                        bool = true;
                    }
                }

            }
        });


    }

    private void beautifyToast(Toast toast){
        View v = toast.getView();
        v.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);

        TextView textView = v.findViewById(android.R.id.message);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        toast.show();
    }



    private void changingColorDynamically() {
        random = new Random();
        randIntBtn = random. nextInt(16);
        try{
            next.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(btnList[randIntBtn])));
        }catch (Exception e){
            Log.d("SAIF", "Exception: ", e);
        }

    }

    private void addingQuestions() {

        // mcq 4 ta, 3ta te 3 ta option, 1ta te 4 ta option

        list.add(new QuestionModel("question 1", "a", "b", "c", null));
        list.add(new QuestionModel("question 2", "a", "b", "c", "d"));
        list.add(new QuestionModel("question 3", "a", "b", "c", null));
        list.add(new QuestionModel("question 4", "a", "b", "c", null));

        // written 8 ta
        list.add(new QuestionModel("question 5", ""));
        list.add(new QuestionModel("question 6", ""));
        list.add(new QuestionModel("question 7", ""));
        list.add(new QuestionModel("question 8", ""));
        list.add(new QuestionModel("question 9", ""));
        list.add(new QuestionModel("question 10", ""));
        list.add(new QuestionModel("question 11", ""));
        list.add(new QuestionModel("question 12", ""));

        // avg question
        list.add(new QuestionModel("question 13", "", "", ""));
        list.add(new QuestionModel("question 14", "", "", ""));
        list.add(new QuestionModel("question 15", "", "", ""));
        list.add(new QuestionModel("question 16", "", "", ""));
        list.add(new QuestionModel("question 17", "", "", ""));
        list.add(new QuestionModel("question 18", "", "", ""));
        list.add(new QuestionModel("question 19", "", "", ""));
        list.add(new QuestionModel("question 20", "", "", ""));
        list.add(new QuestionModel("question 21", "", "", ""));
        list.add(new QuestionModel("question 22", "", "", ""));
        list.add(new QuestionModel("question 23", "", "", ""));
    }

    private void getAnswer(Button selectedOption) {
        enableOption(false); // other options disabled
        next.setEnabled(true);
        next.setAlpha(1);
        Toast toast = Toast.makeText(this, "Ans: " + selectedOption.getText().toString(), Toast.LENGTH_SHORT);
        beautifyToast(toast);
        selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50"))); // make the choosen ans green

    }

    private void enableOption(boolean enable){
        for(int i = 0; i < 4; i++){
            optionsContainer.getChildAt(i).setEnabled(enable);
            if(enable){
                optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898"))); // make the choosen ans green
                //optionsContainer.getChildAt(i);

            }else{
                //optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000"))); // red
            }
        }
    }

    private void initializingAll() {
        answer1 = findViewById(R.id.answer_button1);
        answer1.setVisibility(View.VISIBLE);
        answer2 = findViewById(R.id.answer_button2);
        answer3 = findViewById(R.id.answer_button3);
        answer4 = findViewById(R.id.answer_button4);
        next = findViewById(R.id.button6);
        //tint
        previous = findViewById(R.id.button5);
        questionView = findViewById(R.id.question_textView);
        counterView = findViewById(R.id.question_countView);

        writboxContainer = findViewById(R.id.lower_linearLayout2);
        writboxContainer.setVisibility(View.GONE);
        answerText = findViewById(R.id.answer_editText);

        optionsContainer = findViewById(R.id.upper_linearLayout2);
        optionsContainer.setVisibility(View.VISIBLE);

        avgContainer = findViewById(R.id.layout_avg);
        avgContainer.setVisibility(View.GONE);
        genText = findViewById(R.id.inner_answer_editText2);
        holiText = findViewById(R.id.inner_answer_editText1);
        strText = findViewById(R.id.inner_answer_editText3);
    }

    //adding animations before loading questions
    private void playAnimation(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
        .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(value == 0 && count < 4){
                    String option = "";
                    if(count == 0){
                        option = list.get(position).getAnswerA();
                    }else if(count == 1){
                        option = list.get(position).getAnswerB();
                    }else if(count == 2){
                        option = list.get(position).getAnswerC();
                    }else if(count == 3){
                        try{
                            list.get(position).getAnswerD().equals(null);
                            option = list.get(position).getAnswerD();
                        }catch (NullPointerException ex){
                            answer4.setVisibility(View.GONE);
                        }

                    }
                    playAnimation(optionsContainer.getChildAt(count),0, option);
                    count++;
                }

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if(value == 0){
                    try {
                        ((TextView)view).setText(data);
                        counterView.setText(position+1 + "/" + list.size());
                    }catch (ClassCastException e){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnimation(view, 1, data);
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

    //adding animations before loading questions
    private void playAnimationForWriteContainer(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(value == 0 &&  bool){
                    String option = list.get(position).getAnswerText();
                    playAnimation(writboxContainer.getChildAt(count),0, option);
                }

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if(value == 0 && bool){
                    try {
                        ((TextView)view).setText(data);
                        counterView.setText(position+1 + "/" + list.size());
                    }catch (ClassCastException e){
                        ((EditText)view).setText(data);
                    }
                    view.setTag(data);
                    playAnimation(view, 1, data);
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

    //adding animations before loading questions
    private void playAnimationForAvgContainer(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                if(value == 0 &&  avgBool && count < 3){
                    String option = "";
                    if(count == 0){
                        option = list.get(position).getHoliText();
                    }else if(count == 1){
                        option = list.get(position).getGenText();
                    }else if(count == 2){
                        option = list.get(position).getStrText();
                    }
                    playAnimationForAvgContainer(avgContainer.getChildAt(count),0, option);
                    count++;
                }

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if(value == 0 && bool){
                    try {
                        ((TextView)view).setText(data);
                        counterView.setText(position+1 + "/" + list.size());
                    }catch (ClassCastException e){
                        //((EditText)view).setText(data);
                    }
                    view.setTag(data);
                    playAnimationForAvgContainer(view, 1, data);
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

    private void implementingWaveHeader() {

        waveHeader = findViewById(R.id.waveHeader);

        waveHeader.setColorAlpha(.5f);
        waveHeader.setVelocity(5f);
        waveHeader.setProgress(1f);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(65);
        random = new Random();
        randIntBG = random.nextInt(21);
        try{
            waveHeader.setStartColor(Color.parseColor(bgList[randIntBG]));
            waveHeader.setCloseColor((Color.parseColor(bgList[randIntBG])));
        }catch (Exception e){
            Log.d("SAIF", "Exception: ", e);
        }


        toolbar = findViewById(R.id.question_toolbar);
        setSupportActionBar(toolbar);


        try{
            toolbar.setBackgroundColor(Color.parseColor(bgList[randIntBG]));
        }catch (Exception e){
            Log.d("SAIF", "Exception: ", e);
        }


    }
}
