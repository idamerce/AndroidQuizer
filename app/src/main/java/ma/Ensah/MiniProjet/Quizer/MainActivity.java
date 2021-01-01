package ma.Ensah.MiniProjet.Quizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity {




    class Question{
        int id;
        String question;
        List<String> reponses= new ArrayList<>();
        String reponseCorrect;
        public void addResponse(String reponse){
            reponses.add(reponse);
        }
    }

    //Timer timer;
    public final static int TOTAL_QUESTIONS =20;
    private final static int TIMER_MS =30000; //30 sec/question

    private RadioButton reponse1 ;
    private RadioButton reponse2 ;
    private RadioButton reponse3 ;
    private RadioButton reponse4 ;
    private RadioGroup radiogroup;
    private TextView question;
    private TextView questionsCounterView;
    private TextView scoreView;
    private TextView timerView;
    private TextView ExamTitle;

    private CountDownTimer timer;
    private long passedTimeInMs=0;
    private int score=0;
    private int questionsCounter=1;
    String selectedExam;
    List<Question> questions = new ArrayList<Question>();
    List<Integer> visitedQuestionsId = new ArrayList<>();
    Question currentQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initializer();

        selectedExam = getIntent().getExtras().getString("selectedExam") ;
        ExamTitle.setText("QCM de : "+selectedExam);
        questionsCounterView.setText("Question : "+this.questionsCounter+"/"+ TOTAL_QUESTIONS);

        uploadAllExamQuestions(selectedExam);
        getQuestion();
        fillForme(currentQuestion);

    }

    private void initializer(){
        reponse1     = (RadioButton)findViewById(R.id.reponse1);
        reponse2     = (RadioButton)findViewById(R.id.reponse2);
        reponse3     = (RadioButton)findViewById(R.id.reponse3);
        reponse4     = (RadioButton)findViewById(R.id.reponse4);
        radiogroup   =(RadioGroup)findViewById(R.id.radiogroup);
        question     =  (TextView)findViewById(R.id.question);
        scoreView    =  (TextView)findViewById(R.id.score);
        ExamTitle    =  (TextView)findViewById(R.id.ExamTitle);
        timerView        =  (TextView)findViewById(R.id.timer);
        questionsCounterView =  (TextView)findViewById(R.id.questionsCounterView);
        passedTimeInMs=0;
        score=0;
        questionsCounter=1;
    }

    public void answer(View view){
        checkAnswer();
    }

    public void back(View view) {
        Intent intent = new Intent(this, menu_activity.class);
        startActivity(intent);
    }

    private void EndExam(){
       Intent intent = new Intent(this, ExamResultPopUp.class);
        intent.putExtra("PassedExam",selectedExam);
        intent.putExtra("score",score);
       startActivity(intent);
    }

    private void checkAnswer(){

        timer.cancel();
        if(radiogroup.getCheckedRadioButtonId()!=-1){
            String answer= ((RadioButton)findViewById(radiogroup.getCheckedRadioButtonId())).getText().toString();
            if(answer.equals(currentQuestion.reponseCorrect)){
                Toast.makeText(this, "CORRECT ANSWER", Toast.LENGTH_SHORT).show();
                score++;
                scoreView.setText("Score : "+score);
            }
            else Toast.makeText(this, "WRONG ANSWER  ", Toast.LENGTH_SHORT).show();

        }else Toast.makeText(this, "NO ANSWER", Toast.LENGTH_SHORT).show();
        if(questionsCounter< TOTAL_QUESTIONS){
            getQuestion();
            fillForme(currentQuestion);
            this.questionsCounter++;
            timerView.setTextColor(Color.BLACK);
            questionsCounterView.setText("Question : "+this.questionsCounter+"/"+ TOTAL_QUESTIONS);

        }else{
            EndExam();
        }
    }

    private void startTimer(){
        timer= new CountDownTimer(passedTimeInMs,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                passedTimeInMs = millisUntilFinished;
                updateTimerView();
            }

            @Override
            public void onFinish() {
                    passedTimeInMs=0;
                    updateTimerView();
                    checkAnswer();
            }
        }.start();
    }

    void updateTimerView(){
        int minutes = (int)(passedTimeInMs/1000)/60;
        int seconds = (int)(passedTimeInMs/1000)%60;
        timerView.setText(String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds));
        if(passedTimeInMs<5000)
            timerView.setTextColor(Color.RED);
     }

    private void fillForme(Question currentQuestion) {

        reponse1.setChecked(false);
        reponse2.setChecked(false);
        reponse3.setChecked(false);
        reponse4.setChecked(false);
        radiogroup .clearCheck();

        question.setText(currentQuestion.question);
        reponse1.setText(currentQuestion.reponses.get(0));
        reponse2.setText(currentQuestion.reponses.get(1));
        reponse3.setText(currentQuestion.reponses.get(2));
        reponse4.setText(currentQuestion.reponses.get(3));

        passedTimeInMs = TIMER_MS;
        startTimer();
    }

    private void getQuestion(){
        int randomQuestionId ;
        do{
            randomQuestionId = new Random().nextInt(questions.size());
        }while(visitedQuestionsId.contains(randomQuestionId));
        this.currentQuestion=questions.get(randomQuestionId);
        visitedQuestionsId.add(randomQuestionId);
    }

    private void uploadAllExamQuestions(String filename){

        XmlPullParserFactory pullParserFactory;
        try {
             pullParserFactory = XmlPullParserFactory.newInstance();
             XmlPullParser parser = pullParserFactory.newPullParser();
              InputStream in_s = getAssets().open( "xml/"+filename+".xml");
             parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
             parser.setInput(in_s, null);
              this.questions=  parseXML(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.e("**>","file makaynch XmlPullParserException ");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("**>","file makaynch IOException ");
        }
    }

    private ArrayList<Question> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<Question> tempQuestions = new ArrayList();
        int eventType = parser.getEventType();
        Question question = null;
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equals("element")){
                        question = new Question();
                        tempQuestions.add(question);
                        question.id = Integer.parseInt(parser.getAttributeValue(null,"id"));
                    } else if (question != null){
                        if (name.equals("question")){
                            question.question = parser.nextText();
                        } else if (name.equals("reponse")){
                            question.addResponse(parser.nextText())    ;
                        } else if (name.equals("reponseCorrect")){
                            question.reponseCorrect = parser.nextText();
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
     //   printQuest(tempQuestions);
        return tempQuestions;
    }

    private void printQuest(ArrayList<Question> tempquestions){
        StringBuilder builder= new StringBuilder();
        int i=0;
        for(Question question:tempquestions){
            builder.append(question.id).append("\n")
                    .append(question.question).append("\n")
                    .append(question.reponseCorrect).append("\n")
                    .append(question.reponses.get(0)).append("\n")
                    .append(question.reponses.get(1)).append("\n")
                    .append(question.reponses.get(2)).append("\n")
                    .append(question.reponses.get(3)).append("\n");
         }
            //tt.setText(tt.getText().toString()+builder.toString()+"------") ;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(timer!=null) timer.cancel();
    }

}


