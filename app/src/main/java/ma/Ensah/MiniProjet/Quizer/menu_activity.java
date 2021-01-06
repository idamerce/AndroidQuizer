package ma.Ensah.MiniProjet.Quizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class  menu_activity extends AppCompatActivity {
     private Button btn_java,btn_php,btn_git,btn_html,btn_python,btn_csharp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_activity);
        btn_csharp = (Button)findViewById(R.id.csharp);
        btn_git = (Button)findViewById(R.id.git);
        btn_html = (Button)findViewById(R.id.html_css);
        btn_java = (Button)findViewById(R.id.java);
        btn_php = (Button)findViewById(R.id.php);
        btn_python = (Button)findViewById(R.id.python);
        btn_java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_activity.this, MainActivity.class);
                intent.putExtra("selectedExam","java");
                startActivity(intent);
            }
        });
        btn_python.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_activity.this, MainActivity.class);
                intent.putExtra("selectedExam","python");
                startActivity(intent);
            }
        });
        btn_php.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_activity.this, MainActivity.class);
                intent.putExtra("selectedExam","php");
                startActivity(intent);
            }
        });
        btn_git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_activity.this, MainActivity.class);
                intent.putExtra("selectedExam","git");
                startActivity(intent);
            }
        });
        btn_html.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_activity.this, MainActivity.class);
                intent.putExtra("selectedExam","html-css");
                startActivity(intent);
            }
        });
        btn_csharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_activity.this, MainActivity.class);
                intent.putExtra("selectedExam","c#");
                startActivity(intent);
            }
        });

    }


}
