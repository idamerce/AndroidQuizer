package ma.Ensah.MiniProjet.Quizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ExamResultPopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        popUpConfigurer();

        initializer();

        int score = getIntent().getExtras().getInt ("score");
        String PassedExam = getIntent().getExtras().getString ("PassedExam");

        setImageByScore(score);
        scoreView.setText( score+"/"+MainActivity.TOTAL_QUESTIONS);
        examPassed.setText(PassedExam);
    }

    TextView scoreView ;
    TextView examPassed ;
    ImageView resultImage;

    private void popUpConfigurer(){
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_exam_result_pop_up);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.9),(int)(height*0.95));
    }
    private void initializer(){
        scoreView  =  (TextView)findViewById(R.id.scoreRes);
        examPassed =  (TextView)findViewById(R.id.exam);
        resultImage = (ImageView)findViewById(R.id.resultImage);
    }

    private void setImageByScore(int score) {
        if(score>=MainActivity.TOTAL_QUESTIONS/2){
             resultImage.setImageResource(R.drawable.win);

        }else if(score>=MainActivity.TOTAL_QUESTIONS/4){
            resultImage.setImageResource(R.drawable.loser);
        }else{
            resultImage.setImageResource(R.drawable.bad_loser);
        }
    }

    public void closeApp(View view) {
        finishAffinity();
        System.exit(0);
    }

    public void menu(View view) {
        Intent intent = new Intent(this, menu_activity.class);
        startActivity(intent);
    }
}
