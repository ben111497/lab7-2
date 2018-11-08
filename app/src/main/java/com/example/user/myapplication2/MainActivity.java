package com.example.user.myapplication2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
implements OnCheckedChangeListener,OnClickListener {

    protected EditText tall;
    protected EditText weight;
    protected RadioButton boy;
    protected RadioButton girl;
    protected RadioGroup radioGroup;
    protected Button button;
    protected TextView x1;
    protected TextView x2;
    int gender=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tall=(EditText)findViewById(R.id.tall);
        weight = (EditText)findViewById(R.id.weight);
        boy=(RadioButton)findViewById(R.id.boy);
        girl=(RadioButton)findViewById(R.id.girl);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        button=(Button)findViewById(R.id.button);
        x1=(TextView)findViewById(R.id.x1);
        x2=(TextView)findViewById(R.id.x2);
        button.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(radioGroup.getCheckedRadioButtonId()==R.id.boy)gender=1;
        else gender=2;
    }
    @Override
    public void onClick(View v) {
        runAsyncTask();
    }
    private void runAsyncTask(){
        new AsyncTask<Void,Integer,Boolean>(){
            private ProgressDialog dialog=new ProgressDialog(MainActivity.this);
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                dialog.setMessage("計算中........");
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
            }
            @Override
            protected Boolean doInBackground(Void... voids){
                int progress=0;
                while(progress <= 100){
                    try{
                        Thread.sleep(50);
                        publishProgress(Integer.valueOf(progress));
                        progress++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }return true;
            }
            @Override
            protected void onProgressUpdate(Integer... values){
                super.onProgressUpdate(values);
                dialog.setProgress(values[0]);
            }
            @Override
            protected void onPostExecute(Boolean status){
                dialog.dismiss();
                double cal_Tall=Double.parseDouble(tall.getText().toString());
                double cal_Weight=Double.parseDouble(weight.getText().toString());
                double cal_x1=0;
                double cal_x2=0;
                if(gender==1){
                    cal_x1=22*cal_Tall/100*cal_Tall/100;
                    cal_x2=((cal_Weight-(0.88*cal_x1))*100)/cal_Weight;
                }
                else{
                    cal_x1=22*cal_Tall/100*cal_Tall/100;
                    cal_x2=((cal_Weight-(0.82*cal_x1))*100)/cal_Weight;
                }
                x1.setText(String.format("%.2f",cal_x1));
                x2.setText(String.format("%.2f",cal_x2));
            }
        }.execute();
    }
}
