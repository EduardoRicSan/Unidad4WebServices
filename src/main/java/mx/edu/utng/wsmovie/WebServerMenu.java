package mx.edu.utng.wsmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WebServerMenu extends AppCompatActivity {
    private Button btFirst;
    private Button btSecond;
    private Button btThird;
    private Button btFourth;
    private Button btFifth;
    private Button btSixth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_server_menu);
        btFirst = (Button)findViewById(R.id.bt_firs_ws);
        btSecond = (Button)findViewById(R.id.bt_second_ws);
        btThird = (Button)findViewById(R.id.bt_third_ws);
        btFourth = (Button)findViewById(R.id.bt_fourth_ws);
        btFifth = (Button)findViewById(R.id.bt_fifth_ws);
        btSixth = (Button)findViewById(R.id.bt_sixth_ws);

        btFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WebServerMenu.this, MainActivity.class));
            }
        });
        btSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WebServerMenu.this, DocumentMain.class));
            }
        });
        btThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WebServerMenu.this, CompanyActivity.class));
            }
        });
        btFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WebServerMenu.this, MeActivity.class));
            }
        });
        btFifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WebServerMenu.this, FruitActivity.class));
            }
        });
        btSixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WebServerMenu.this, SongActivity.class));
            }
        });


    }
}
