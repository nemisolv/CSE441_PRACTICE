package net.nemisolv.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void,Integer,Void> {
    Activity parentActivity;

    public MyAsyncTask(Activity activity) {
        parentActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(parentActivity, "onPreExecute!",
                Toast.LENGTH_LONG).show();
    }
    @Override
    protected Void doInBackground(Void... arg0) {
        for(int i=0;i<=100;i++)
        {
            SystemClock.sleep(100);
            publishProgress(i);
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ProgressBar paCha= parentActivity.findViewById(R.id.progressBar);
        int value=values[0];
        paCha.setProgress(value);
        TextView
                txtMsg= parentActivity.findViewById(R.id.txt_percentage);
        txtMsg.setText(value+"%");
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Toast.makeText(parentActivity, "Update Complete!",
                Toast.LENGTH_LONG).show();
    }
}
