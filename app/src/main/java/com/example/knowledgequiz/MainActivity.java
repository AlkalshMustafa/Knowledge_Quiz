 package com.example.knowledgequiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knowledgequiz.data.AnswerListAsyncResponse;
import com.example.knowledgequiz.data.QuestionBank;
import com.example.knowledgequiz.model.Question;

import java.util.ArrayList;
import java.util.List;

import static com.example.knowledgequiz.R.color.creamy;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener {


     private TextView    questionTextView;
     private TextView    questionCounterTextView;
     private Button      trueButton;
     private Button      falseButton;
     private ImageButton nextButton;
     private ImageButton prevButton;

     private int currentQuestionIndex = 0;
     private List<Question> questionList;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         nextButton = findViewById(R.id.next_button);
         prevButton = findViewById(R.id.prev_button);
         trueButton = findViewById(R.id.true_button);
         falseButton = findViewById(R.id.false_button);
         questionCounterTextView = findViewById(R.id.counter_text);
         questionTextView = findViewById(R.id.question_textview);

         nextButton.setOnClickListener(this);
         prevButton.setOnClickListener(this);
         trueButton.setOnClickListener(this);
         falseButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @SuppressLint("SetTextI18n")
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                questionCounterTextView.setText(currentQuestionIndex + 1 + " out of: "
                        + (1 + questionArrayList.size())); // 0 out of: 234

                Log.d("Inside", "processFinished: " + questionArrayList);

            }
        });
    }

     @Override
     public void onClick(View v) {
         switch (v.getId()) {
             case R.id.prev_button:
                 if (currentQuestionIndex > 0) {
                     currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                     updateQuestion();
                 }
                 break;
             case R.id.next_button:
                 if (currentQuestionIndex < questionList.size()) {
                     currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                     updateQuestion();
                 }
                 break;
             case R.id.true_button:
                 checkAnswer(true);
                 updateQuestion();
                 break;
             case R.id.false_button:
                 checkAnswer(false);
                 updateQuestion();
                 break;
         }
     }

     private void updateQuestion() {
         String question = questionList.get(currentQuestionIndex).getAnswer();
         questionTextView.setText(question);
         questionCounterTextView.setText(currentQuestionIndex + 1 + " out of: " + ( 1 + questionList.size()));
     }

     private void checkAnswer(boolean userChooseCorrect) {
         boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();
         int toastMessageId = 0;
         if (userChooseCorrect == answerIsTrue) {

             fadeView();
             toastMessageId = R.string.correct_answer;
         } else {
             shakeAnimation();
             toastMessageId = R.string.wrong_answer;
         }
         Toast.makeText(MainActivity.this, toastMessageId,
                 Toast.LENGTH_SHORT)
                 .show();
     }


     // ===================== setUp animations =====================

     private void shakeAnimation() {
         Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                 R.anim.shake_animation);
         final CardView cardView = findViewById(R.id.cardView);
         cardView.setAnimation(shake);

         shake.setAnimationListener(new Animation.AnimationListener() {
             @Override
             public void onAnimationStart(Animation animation) {
                 cardView.setCardBackgroundColor(Color.RED);

             }

             @Override
             public void onAnimationEnd(Animation animation) {
                 cardView.setCardBackgroundColor(Color.rgb(254,234,195));

             }

             @Override
             public void onAnimationRepeat(Animation animation) {

             }
         });


     }

     private void fadeView() {
         final CardView cardView = findViewById(R.id.cardView);
         AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f); // 1.0f visible // 0.0f invisible

         alphaAnimation.setDuration(350);
         alphaAnimation.setRepeatCount(1);
         alphaAnimation.setRepeatMode(Animation.REVERSE);

         cardView.setAnimation(alphaAnimation);

         alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
             @Override
             public void onAnimationStart(Animation animation) {
                 cardView.setCardBackgroundColor(Color.rgb(1 ,163,255));
             }

             @Override
             public void onAnimationEnd(Animation animation) {
                 cardView.setCardBackgroundColor(Color.rgb(254,234,195));
             }

             @Override
             public void onAnimationRepeat(Animation animation) {

             }
         });
     }
 }
