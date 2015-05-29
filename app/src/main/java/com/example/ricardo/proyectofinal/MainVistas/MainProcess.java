package com.example.ricardo.proyectofinal.MainVistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ricardo.proyectofinal.*;
import com.example.ricardo.proyectofinal.Process;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laboratorio on 25/05/2015.
 */
public class MainProcess extends AppCompatActivity implements DownF {

    private ListView listView;
    private ProcAdap adapter;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        Bundle extras = getIntent().getExtras();

        listView = (ListView) findViewById(R.id.process);
        textView = (TextView) findViewById(R.id.textView2);
        ConnectJson process = new ConnectJson(this);
        String name = extras.getString("name");
        Long id = extras.getLong("id");
        textView.setText(textView.getText()+" "+name);
        process.execute("https://dynamicformapi.herokuapp.com/procedures/by_group/"+id+".json");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(MainProcess.this,MainStep.class);
                intent.putExtra("name",adapter.getName(position));
                intent.putExtra("id",adapter.getItemId(position));
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

    @Override
    public void OnFinish(String obj) {
        List<Process> processes = new ArrayList<>();
        JSONArray array = null;

        try {
            array = new JSONArray(obj);
            for(int i=0; i< array.length(); i++){
                JSONObject item = null;
                try {
                    item = array.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Process process = null;
                try {
                    process = new Process(item.getString("name"),item.getInt("procedure_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                processes.add(process);
            }

            adapter = new ProcAdap(processes,this);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
