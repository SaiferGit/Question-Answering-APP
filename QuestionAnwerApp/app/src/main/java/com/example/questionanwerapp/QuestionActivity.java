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
    Toolbar toolbar;

    private int position = 0;
    private int count = 0;

    private final String[] bgList = {
            "#CF9A41", "#EE9198", "#FFAD00", "#6C3C26", "#C7A338", "#7E412E", "#522B1A",
            "#2C112A", "#044660", "#08B3AB", "#000000", "#BB5769", "#00496A", "#776CB2",
            "#CCBCA5", "#31273B", "#CDD6D5", "#33444E", "#A33A47", "#050B2B", "#495B53", "#002540"
    };

    private final String[] btnList = {
            "#048D79", "#F4B504", "#0B1F33", "#290000", "#58221C", "#D67B80", "#8E3463",
            "#844257", "#013554", "#EA4136", "#98D3E1", "#667FB5", "#79963C", "#212123",
            "#000000", "#5A4692", "#142E54"
    };

    private int randIntBG = 0, randIntBtn = 0;
    Random random = new Random();

    private boolean bool = false;
    private boolean avgBool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initializingAll();
        implementingWaveHeader();

        list = new ArrayList<>();
        addingQuestions();

        answer1.setOnClickListener(v -> getAnswer(answer1));
        answer2.setOnClickListener(v -> getAnswer(answer2));
        answer3.setOnClickListener(v -> getAnswer(answer3));
        answer4.setOnClickListener(v -> getAnswer(answer4));

        if (!list.isEmpty()) {
            playAnimation(questionView, 0, list.get(position).getQuestion());
        }

        next.setOnClickListener(v -> handleNextButton());
    }

    private void handleNextButton() {
        implementingWaveHeader();
        changingColorDynamically();

        if (bool) {
            String editText = answerText.getText().toString().trim();

            if (TextUtils.isEmpty(editText)) {
                answerText.requestFocus();
                answerText.setError("Hey! you forgot to type your answer");
                return;
            } else {
                showStyledToast("Ans: " + editText);
            }
        }

        if (avgBool) {
            String editText1 = holiText.getText().toString().trim();
            String editText2 = genText.getText().toString().trim();
            String editText3 = strText.getText().toString().trim();

            if (TextUtils.isEmpty(editText1)) {
                holiText.requestFocus();
                holiText.setError("Hey! you forgot to type here");
                return;
            }

            if (TextUtils.isEmpty(editText2)) {
                genText.requestFocus();
                genText.setError("Hey! you forgot to type here");
                return;
            }

            if (TextUtils.isEmpty(editText3)) {
                strText.requestFocus();
                strText.setError("Hey! you forgot to type here");
                return;
            }

            showStyledToast("Ans1: " + editText1);
            showStyledToast("Ans2: " + editText2);
            showStyledToast("Ans3: " + editText3);
        }

        if (answer4.getVisibility() == View.GONE) {
            answer4.setVisibility(View.VISIBLE);
        }

        enableOption(true);

        if (position >= list.size() - 1) {
            showStyledToast("Thank you for your Data");
            startActivity(new Intent(QuestionActivity.this, MainActivity.class));
            finish();
            return;
        }

        position++;
        count = 0;

        if (position < 4) {
            bool = false;
            avgBool = false;
            next.setEnabled(false);
            next.setAlpha(0.5f);

            optionsContainer.setVisibility(View.VISIBLE);
            writboxContainer.setVisibility(View.GONE);
            avgContainer.setVisibility(View.GONE);

            playAnimation(questionView, 0, list.get(position).getQuestion());

        } else if (position < 12) {
            bool = true;
            avgBool = false;

            optionsContainer.setVisibility(View.GONE);
            writboxContainer.setVisibility(View.VISIBLE);
            avgContainer.setVisibility(View.GONE);

            answerText.getText().clear();
            next.setEnabled(true);
            next.setAlpha(1f);

            try {
                playAnimationForWriteContainer(questionView, 0, list.get(position).getQuestion());
            } catch (Exception e) {
                Log.d("SAIF", "Exception:", e);
            }

        } else {
            bool = false;
            avgBool = true;

            optionsContainer.setVisibility(View.GONE);
            writboxContainer.setVisibility(View.GONE);
            avgContainer.setVisibility(View.VISIBLE);

            genText.getText().clear();
            holiText.getText().clear();
            strText.getText().clear();

            next.setEnabled(true);
            next.setAlpha(1f);

            try {
                playAnimationForAvgContainer(questionView, 0, list.get(position).getQuestion());
            } catch (Exception e) {
                Log.d("SAIF", "Exception:", e);
            }
        }
    }

    private void showStyledToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        View v = toast.getView();

        if (v != null && v.getBackground() != null) {
            v.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        }

        TextView textView = (v != null) ? v.findViewById(android.R.id.message) : null;
        if (textView != null) {
            textView.setTextColor(Color.parseColor("#FFFFFF"));
        }

        toast.show();
    }

    private void changingColorDynamically() {
        random = new Random();
        randIntBtn = random.nextInt(btnList.length);

        try {
            next.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(btnList[randIntBtn])));
        } catch (Exception e) {
            Log.d("SAIF", "Exception: ", e);
        }
    }

    private void addingQuestions() {
        list.add(new QuestionModel("question 1", "a", "b", "c", null));
        list.add(new QuestionModel("question 2", "a", "b", "c", "d"));
        list.add(new QuestionModel("question 3", "a", "b", "c", null));
        list.add(new QuestionModel("question 4", "a", "b", "c", null));

//        list.add(new QuestionModel("question 5", ""));
//        list.add(new QuestionModel("question 6", ""));
//        list.add(new QuestionModel("question 7", ""));
//        list.add(new QuestionModel("question 8", ""));
//        list.add(new QuestionModel("question 9", ""));
//        list.add(new QuestionModel("question 10", ""));
//        list.add(new QuestionModel("question 11", ""));
//        list.add(new QuestionModel("question 12", ""));
//
//        list.add(new QuestionModel("question 13", "", "", ""));
//        list.add(new QuestionModel("question 14", "", "", ""));
//        list.add(new QuestionModel("question 15", "", "", ""));
//        list.add(new QuestionModel("question 16", "", "", ""));
//        list.add(new QuestionModel("question 17", "", "", ""));
//        list.add(new QuestionModel("question 18", "", "", ""));
//        list.add(new QuestionModel("question 19", "", "", ""));
//        list.add(new QuestionModel("question 20", "", "", ""));
//        list.add(new QuestionModel("question 21", "", "", ""));
//        list.add(new QuestionModel("question 22", "", "", ""));
//        list.add(new QuestionModel("question 23", "", "", ""));
    }

    private void getAnswer(Button selectedOption) {
        if (selectedOption == null) return;

        enableOption(false);
        next.setEnabled(true);
        next.setAlpha(1f);

        showStyledToast("Ans: " + selectedOption.getText().toString());

        try {
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        } catch (Exception e) {
            Log.d("SAIF", "Button tint error: ", e);
        }
    }

    private void enableOption(boolean enable) {
        if (optionsContainer == null) return;

        int childCount = optionsContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = optionsContainer.getChildAt(i);
            if (child == null) continue;

            child.setEnabled(enable);

            if (enable) {
                try {
                    child.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
                } catch (Exception e) {
                    Log.d("SAIF", "EnableOption tint error: ", e);
                }
            }
        }
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

        writboxContainer = findViewById(R.id.lower_linearLayout2);
        optionsContainer = findViewById(R.id.upper_linearLayout2);
        avgContainer = findViewById(R.id.layout_avg);

        answerText = findViewById(R.id.answer_editText);
        genText = findViewById(R.id.inner_answer_editText2);
        holiText = findViewById(R.id.inner_answer_editText1);
        strText = findViewById(R.id.inner_answer_editText3);

        if (answer1 != null) answer1.setVisibility(View.VISIBLE);
        if (writboxContainer != null) writboxContainer.setVisibility(View.GONE);
        if (optionsContainer != null) optionsContainer.setVisibility(View.VISIBLE);
        if (avgContainer != null) avgContainer.setVisibility(View.GONE);
        if (next != null) {
            next.setEnabled(false);
            next.setAlpha(0.5f);
        }
    }

    private void playAnimation(final View view, final int value, final String data) {
        if (view == null) return;

        view.animate()
                .alpha(value)
                .scaleX(value)
                .scaleY(value)
                .setDuration(500)
                .setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        if (value == 0 && count < 4) {
                            String option = "";

                            if (count == 0) option = list.get(position).getAnswerA();
                            else if (count == 1) option = list.get(position).getAnswerB();
                            else if (count == 2) option = list.get(position).getAnswerC();
                            else if (count == 3) {
                                if (list.get(position).getAnswerD() != null) {
                                    option = list.get(position).getAnswerD();
                                } else if (answer4 != null) {
                                    answer4.setVisibility(View.GONE);
                                }
                            }

                            View child = optionsContainer.getChildAt(count);
                            if (child != null) {
                                playAnimation(child, 0, option);
                            }
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (value == 0) {
                            try {
                                ((TextView) view).setText(data);
                                if (counterView != null) {
                                    counterView.setText((position + 1) + "/" + list.size());
                                }
                            } catch (ClassCastException e) {
                                try {
                                    ((Button) view).setText(data);
                                } catch (Exception ignored) {
                                }
                            }
                            view.setTag(data);
                            playAnimation(view, 1, data);
                        }
                    }

                    @Override public void onAnimationCancel(Animator animator) {}
                    @Override public void onAnimationRepeat(Animator animator) {}
                });
    }

    private void playAnimationForWriteContainer(final View view, final int value, final String data) {
        if (view == null) return;

        view.animate()
                .alpha(value)
                .scaleX(value)
                .scaleY(value)
                .setDuration(500)
                .setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        if (value == 0 && bool && writboxContainer != null && writboxContainer.getChildCount() > 0) {
                            String option = list.get(position).getAnswerText();
                            playAnimation(writboxContainer.getChildAt(0), 0, option);
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (value == 0 && bool) {
                            try {
                                ((TextView) view).setText(data);
                                if (counterView != null) {
                                    counterView.setText((position + 1) + "/" + list.size());
                                }
                            } catch (ClassCastException e) {
                                try {
                                    ((EditText) view).setText(data);
                                } catch (Exception ignored) {
                                }
                            }
                            view.setTag(data);
                            playAnimationForWriteContainer(view, 1, data);
                        }
                    }

                    @Override public void onAnimationCancel(Animator animator) {}
                    @Override public void onAnimationRepeat(Animator animator) {}
                });
    }

    private void playAnimationForAvgContainer(final View view, final int value, final String data) {
        if (view == null) return;

        view.animate()
                .alpha(value)
                .scaleX(value)
                .scaleY(value)
                .setDuration(500)
                .setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        if (value == 0 && avgBool && count < 3 && avgContainer != null) {
                            String option = "";

                            if (count == 0) option = list.get(position).getHoliText();
                            else if (count == 1) option = list.get(position).getGenText();
                            else if (count == 2) option = list.get(position).getStrText();

                            View child = avgContainer.getChildAt(count);
                            if (child != null) {
                                playAnimationForAvgContainer(child, 0, option);
                            }
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (value == 0 && avgBool) {
                            try {
                                ((TextView) view).setText(data);
                                if (counterView != null) {
                                    counterView.setText((position + 1) + "/" + list.size());
                                }
                            } catch (ClassCastException e) {
                                if (view instanceof EditText) {
                                    ((EditText) view).setText(data);
                                }
                            }
                            view.setTag(data);
                            playAnimationForAvgContainer(view, 1, data);
                        }
                    }

                    @Override public void onAnimationCancel(Animator animator) {}
                    @Override public void onAnimationRepeat(Animator animator) {}
                });
    }

    private void implementingWaveHeader() {
        waveHeader = findViewById(R.id.waveHeader);

        if (waveHeader != null) {
            waveHeader.setColorAlpha(.5f);
            waveHeader.setVelocity(5f);
            waveHeader.setProgress(1f);
            waveHeader.setGradientAngle(45);
            waveHeader.setWaveHeight(65);

            random = new Random();
            randIntBG = random.nextInt(bgList.length);

            try {
                waveHeader.setStartColor(Color.parseColor(bgList[randIntBG]));
                waveHeader.setCloseColor(Color.parseColor(bgList[randIntBG]));
            } catch (Exception e) {
                Log.d("SAIF", "Exception: ", e);
            }
        }

        toolbar = findViewById(R.id.question_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            try {
                toolbar.setBackgroundColor(Color.parseColor(bgList[randIntBG]));
            } catch (Exception e) {
                Log.d("SAIF", "Exception: ", e);
            }
        }
    }
}
