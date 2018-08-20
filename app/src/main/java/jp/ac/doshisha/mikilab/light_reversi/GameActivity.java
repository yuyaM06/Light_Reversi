package jp.ac.doshisha.mikilab.light_reversi;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static ArrayList<Light> Lights;

    int count = 0;
    int flag = 0;
    int k[] = {-1, -1};

    Board b = new Board();
    Player Me = new Player();
    Player CPU = new Player();

    final ArrayList<Light> myLights = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /*照明制御ありver
        // ネットワーク設定
        SocketClient.setEndpoint(new InetSocketAddress("172.20.11.58", 44344));

        // lightの情報をサーバから持ってくる
        new Thread(new Runnable() {
            @Override
            public void run(){
                Lights = SocketClient.getLights();
            }
        }).start();


        for(int i=0; i<36; i++) myLights.add(Lights.get(i));

        for(int i=0; i<36; i++){
            if(b.st[i/6][i%6] == 1){
                myLights.get(i).setLumPct(50.0);
                myLights.get(i).setTemperature(4000.0);
            }
            else if(b.st[i/6][i%6] == -1){
                myLights.get(i).setLumPct(50.0);
                myLights.get(i).setTemperature(1000.0);
            }
            else myLights.get(i).setLumPct(0.0);
        }
        */

    }

    public void ChangePoint(View v){
        switch(v.getId()){
            case R.id.button0:
                TextView tv = findViewById(R.id.textView_mypoint);
                Button b = findViewById(R.id.button0);
                count++;
                tv.setText(count+" point");
                if(count > 5) {
                    //b.setBackgroundColor(R.drawable.frame_style);
                    b.setBackgroundColor(Color.rgb(0, 100, 200));
                }
                break;
        }
    }

    /* 照明制御
    public void RefleshLight(){
        // 各照明に%を設定する
        for(int i=0; i<36; i++){
            if(b.st[i/6][i%6] == 1){
                myLights.get(i).setLumPct(50.0);
                myLights.get(i).setTemperature(4000.0);
            }
            else if(b.st[i/6][i%6] == -1){
                myLights.get(i).setLumPct(50.0);
                myLights.get(i).setTemperature(1000.0);
            }
            else myLights.get(i).setLumPct(0.0);
        }
    }
    */

    public void ModeChange(View v){
        String message;
        Context context = getApplicationContext();

        b.count();
        if(b.num[0] + b.num[1] == 4 || b.num[0] + b.num[1] == 5){
            if(b.mode == 1) {
                b.mode = 2;
                message = "CPU先攻";
                /* CPUの手 */
                k = b.computer();
                b.set(k);
                RefleshGUI(v);
            }
            else {
                b.mode = 1;
                message = "Me先攻";
                b.init();
                RefleshGUI(v);
            }
            Toast.makeText(context , message, Toast.LENGTH_SHORT).show();
        }
    }

    public void RefleshGUI(View v){

        Button[] bArray = new Button[36];
        bArray[0] = findViewById(R.id.button0);bArray[1] = findViewById(R.id.button1);bArray[2] = findViewById(R.id.button2);
        bArray[3] = findViewById(R.id.button3);bArray[4] = findViewById(R.id.button4);bArray[5] = findViewById(R.id.button5);
        bArray[6] = findViewById(R.id.button6);bArray[7] = findViewById(R.id.button7);bArray[8] = findViewById(R.id.button8);
        bArray[9] = findViewById(R.id.button9);bArray[10] = findViewById(R.id.button10);bArray[11] = findViewById(R.id.button11);
        bArray[12] = findViewById(R.id.button12);bArray[13] = findViewById(R.id.button13);bArray[14] = findViewById(R.id.button14);
        bArray[15] = findViewById(R.id.button15);bArray[16] = findViewById(R.id.button16);bArray[17] = findViewById(R.id.button17);
        bArray[18] = findViewById(R.id.button18);bArray[19] = findViewById(R.id.button19);bArray[20] = findViewById(R.id.button20);
        bArray[21] = findViewById(R.id.button21);bArray[22] = findViewById(R.id.button22);bArray[23] = findViewById(R.id.button23);
        bArray[24] = findViewById(R.id.button24);bArray[25] = findViewById(R.id.button25);bArray[26] = findViewById(R.id.button26);
        bArray[27] = findViewById(R.id.button27);bArray[28] = findViewById(R.id.button28);bArray[29] = findViewById(R.id.button29);
        bArray[30] = findViewById(R.id.button30);bArray[31] = findViewById(R.id.button31);bArray[32] = findViewById(R.id.button32);
        bArray[33] = findViewById(R.id.button33);bArray[34] = findViewById(R.id.button34);bArray[35] = findViewById(R.id.button35);

        TextView mytv = findViewById(R.id.textView_mypoint);
        TextView cputv = findViewById(R.id.textView_cpupoint);

        Context context = getApplicationContext();
        String message ="";

        for(int i=0; i<36; i++){
            if(b.st[i/6][i%6] == 1){
                bArray[i].setBackgroundColor(Color.rgb(0, 0, 200));
                //bArray[i].getTextColors();
            }
            else if(b.st[i/6][i%6] == -1){
                bArray[i].setBackgroundColor(Color.rgb(200, 0, 0));
            }
            else bArray[i].setBackgroundColor(Color.rgb(10, 10, 10));
        }

        b.count();
        if(b.mode == 1) {
            mytv.setText(b.num[0] + " point");
            cputv.setText(b.num[1] + " point");
        }
        else{
            mytv.setText(b.num[1] + " point");
            cputv.setText(b.num[0] + " point");
        }

        System.out.println("GUIを更新!!");

        if(b.flag == 1) message = "あなたの勝ち!!";
        else if(b.flag == 9) message = "CPUの勝ち！！";
        else if(b.flag == 0)  message = "引き分け";

        if(b.flag >= 0) Toast.makeText(context , message, Toast.LENGTH_LONG).show();
    }

    public void putPiece(View v){

        int bnum = -1;
        int judge = 0;

        switch(v.getId()){
            case R.id.button0:
                bnum = 0;
                Me.ppos[0] = 0;
                Me.ppos[1] = 0;
                break;
            case R.id.button1:
                bnum = 1;
                Me.ppos[0] = 0;
                Me.ppos[1] = 1;
                break;
            case R.id.button2:
                bnum = 2;
                Me.ppos[0] = 0;
                Me.ppos[1] = 2;
                break;
            case R.id.button3:
                bnum = 3;
                Me.ppos[0] = 0;
                Me.ppos[1] = 3;
                break;
            case R.id.button4:
                bnum = 4;
                Me.ppos[0] = 0;
                Me.ppos[1] = 4;
                break;
            case R.id.button5:
                bnum = 5;
                Me.ppos[0] = 0;
                Me.ppos[1] = 5;
                break;
            case R.id.button6:
                bnum = 6;
                Me.ppos[0] = 1;
                Me.ppos[1] = 0;
                break;
            case R.id.button7:
                bnum = 7;
                Me.ppos[0] = 1;
                Me.ppos[1] = 1;
                break;
            case R.id.button8:
                bnum = 8;
                Me.ppos[0] = 1;
                Me.ppos[1] = 2;
                break;
            case R.id.button9:
                bnum = 9;
                Me.ppos[0] = 1;
                Me.ppos[1] = 3;
                break;
            case R.id.button10:
                bnum = 10;
                Me.ppos[0] = 1;
                Me.ppos[1] = 4;
                break;
            case R.id.button11:
                bnum = 11;
                Me.ppos[0] = 1;
                Me.ppos[1] = 5;
                break;
            case R.id.button12:
                bnum = 12;
                Me.ppos[0] = 2;
                Me.ppos[1] = 0;
                break;
            case R.id.button13:
                bnum = 13;
                Me.ppos[0] = 2;
                Me.ppos[1] = 1;
                break;
            case R.id.button14:
                bnum = 14;
                Me.ppos[0] = 2;
                Me.ppos[1] = 2;
                break;
            case R.id.button15:
                bnum = 15;
                Me.ppos[0] = 2;
                Me.ppos[1] = 3;
                break;
            case R.id.button16:
                bnum = 16;
                Me.ppos[0] = 2;
                Me.ppos[1] = 4;
                break;
            case R.id.button17:
                bnum = 17;
                Me.ppos[0] = 2;
                Me.ppos[1] = 5;
                break;
            case R.id.button18:
                bnum = 18;
                Me.ppos[0] = 3;
                Me.ppos[1] = 0;
                break;
            case R.id.button19:
                bnum = 19;
                Me.ppos[0] = 3;
                Me.ppos[1] = 1;
                break;
            case R.id.button20:
                bnum = 20;
                Me.ppos[0] = 3;
                Me.ppos[1] = 2;
                break;
            case R.id.button21:
                bnum = 21;
                Me.ppos[0] = 3;
                Me.ppos[1] = 3;
                break;
            case R.id.button22:
                bnum = 22;
                Me.ppos[0] = 3;
                Me.ppos[1] = 4;
                break;
            case R.id.button23:
                bnum = 23;
                Me.ppos[0] = 3;
                Me.ppos[1] = 5;
                break;
            case R.id.button24:
                bnum = 24;
                Me.ppos[0] = 4;
                Me.ppos[1] = 0;
                break;
            case R.id.button25:
                bnum = 25;
                Me.ppos[0] = 4;
                Me.ppos[1] = 1;
                break;
            case R.id.button26:
                bnum = 26;
                Me.ppos[0] = 4;
                Me.ppos[1] = 2;
                break;
            case R.id.button27:
                bnum = 27;
                Me.ppos[0] = 4;
                Me.ppos[1] = 3;
                break;
            case R.id.button28:
                bnum = 28;
                Me.ppos[0] = 4;
                Me.ppos[1] = 4;
                break;
            case R.id.button29:
                bnum = 29;
                Me.ppos[0] = 4;
                Me.ppos[1] = 5;
                break;
            case R.id.button30:
                bnum = 30;
                Me.ppos[0] = 5;
                Me.ppos[1] = 0;
                break;
            case R.id.button31:
                bnum = 31;
                Me.ppos[0] = 5;
                Me.ppos[1] = 1;
                break;
            case R.id.button32:
                bnum = 32;
                Me.ppos[0] = 5;
                Me.ppos[1] = 2;
                break;
            case R.id.button33:
                bnum = 33;
                Me.ppos[0] = 5;
                Me.ppos[1] = 3;
                break;
            case R.id.button34:
                bnum = 34;
                Me.ppos[0] = 5;
                Me.ppos[1] = 4;
                break;
            case R.id.button35:
                bnum = 35;
                Me.ppos[0] = 5;
                Me.ppos[1] = 5;
                break;
        }

        //for debug
        System.out.println(bnum+"has been pushed!");
        System.out.println("("+Me.ppos[0]+","+Me.ppos[1]+")");

        judge = b.actionPerformed(Me.ppos);
        for(int i=0; i<6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(b.st[i][j]+" ");
            }
            System.out.println();
        }

        RefleshGUI(v);

        /* 照明制御ver */
        //RefleshLight();

        flag = 1;

        if(judge == 9){
            try {
                Thread.sleep(2000); //2000ミリ秒Sleepする
            } catch (InterruptedException e) {
            }
            /* CPUの手 */
            k = b.computer();
            b.set(k);
            RefleshGUI(v);
        }
    }

    public void Return(View v){
        b.init();
        b.b_w = -b.b_w;
        RefleshGUI(v);
        b.flag = -1;
    }

}
