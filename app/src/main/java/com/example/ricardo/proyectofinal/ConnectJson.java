package com.example.ricardo.proyectofinal;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Ricardo on 19/05/2015.
 */
public class ConnectJson extends AsyncTask<String,Integer,String> {

    private ListView listView;
    private String contend;
    private String enlace;
    List<CatAdap> catAdapList;
    DownF F;

    public ConnectJson(DownF f) {
        F = f;
    }

    @Override
    protected void onPostExecute(String jsonObject) {
    F.OnFinish(jsonObject);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection con = null;
        BufferedReader bufferedReader= null;
        String content=" ";
        try {
            URL url = new URL(params[0]);
             con = (HttpURLConnection) url.openConnection();
             bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while((line = bufferedReader.readLine())!=null){
                content +=line;
            }
            bufferedReader.close();

        } catch (IOException e) {
            Log.e(e.toString(), e.getMessage());
        } finally {
            if (con!= null)
                con.disconnect();
        }
        if (content != "") {
            return content;
        }
        return null;
    }
}
