package com.vinrosa.primeasynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mProgress, mResult;
    EditText mInput;
    AsyncTask<String, Integer, Boolean> asyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.myButton).setOnClickListener(this);
        mInput = (EditText) findViewById(R.id.myInput);
        mProgress = (TextView) findViewById(R.id.myProgress);
        mResult = (TextView) findViewById(R.id.myResult);
    }

    @Override
    protected void onPause() {
        if (asyncTask != null){
            asyncTask.cancel(true);
            asyncTask = null;
        }
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        if (asyncTask != null){
            asyncTask.cancel(true);
            asyncTask = null;
        }
        asyncTask =
                new AsyncTask<String, Integer, Boolean>() {
                    @Override
                    protected Boolean doInBackground(String... strings) {
                        int param = Integer.parseInt(strings[0]);
                        for (int i = 0; i < param; i++) {
                            publishProgress(i);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return isPrime(param);
                    }

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        mResult.setText("");
                        mProgress.setText("");
                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        mProgress.setText(String.format("%d %s", values[0],
                            isPrime(values[0]) ? " es primo"  : "no es primo"
                        ));
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        if (aBoolean){
                            mResult.setText(mInput.getText().toString() + " Es Primo");
                        } else {
                            mResult.setText(mInput.getText().toString() + " No Es Primo");
                        }
                    }
                };
        asyncTask.execute(mInput.getText().toString());

    }

    boolean isPrime(int n) {
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
