package jp.ac.doshisha.mikilab.light_reversi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.button_start:
                Intent intent = new Intent(getApplication(), GameActivity.class);
                //遷移先の画面起動
                startActivity(intent);
                break;
        }
    }

}
