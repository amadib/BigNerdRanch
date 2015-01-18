package geoquiz.android.bignerdranch.com.geoquiz;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class QuizActivity extends Activity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPreviousButton;
    private Button mNextButton;

    private TextView mQuestionTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_oceans, true),
    };

    private LinkedList<Integer> mPreviousIndex = new LinkedList<Integer>();
    private int mCurrentIndex = 0;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    // SHORTCUT APPLE+F12 - go to method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
                updateQuestion();
            }
        });

        // SHORTCUT Auto-suggest fix (Alt+Enter)
        // get reference to inflated Views
        mTrueButton = (Button) findViewById(R.id.true_button);


        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
                updateQuestion();
            }
        });

        
        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPreviousIndex.size() != 0){
                    mCurrentIndex = mPreviousIndex.removeLast().intValue();
                    updateQuestion();
                }
            }
        });

        // Setting listeners
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        // SHORTCUT Bookmark: (ctrl+shift+0)
        // SHORTCUT Move up & down: ALT+shift+ARROW KEYS)
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        updateQuestion();
    }

    private void nextQuestion() {
        mPreviousIndex.add(mCurrentIndex);
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    private void checkAnswer(boolean userPressedTrue){

        int messageResId = messageResId = R.string.incorrect_toast;;

        if (userPressedTrue == mQuestionBank[mCurrentIndex].isTrueQuestion() ||
                !userPressedTrue == !mQuestionBank[mCurrentIndex].isTrueQuestion() ){
            messageResId = R.string.correct_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
