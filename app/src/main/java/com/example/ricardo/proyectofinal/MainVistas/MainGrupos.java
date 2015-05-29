package com.example.ricardo.proyectofinal.MainVistas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ricardo.proyectofinal.CatAdap;
import com.example.ricardo.proyectofinal.ConnectJson;
import com.example.ricardo.proyectofinal.DownF;
import com.example.ricardo.proyectofinal.ListAdapter;
import com.example.ricardo.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainGrupos extends AppCompatActivity implements DownF {

    private ListView listView;
    private ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

         listView = (ListView) findViewById(R.id.Categoria);
        ConnectJson category = new ConnectJson(this);
        category.execute("https://dynamicformapi.herokuapp.com/groups.json");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainGrupos.this, MainProcess.class);
                intent.putExtra("name", adapter.getName(position));
                intent.putExtra("id", adapter.getItemId(position));
               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }


    @Override
    public void OnFinish(String Jobj) {
        List<CatAdap> categot = new ArrayList<>();

        JSONArray array = null;
        try {
            array = new JSONArray(Jobj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < array.length(); i++) {
            JSONObject item = null;
            try {
                item = array.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CatAdap grupo = null;
            try {
                grupo = new CatAdap(item.getInt("group_id"), item.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            categot.add(grupo);

        }
        adapter = new ListAdapter(this, categot);
        listView.setAdapter(adapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_final, menu);
        return true;
    }

    private boolean Network(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =  manager.getActiveNetworkInfo();

        boolean isNetworkAvaible = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvaible = true;
            Toast.makeText(this, "Network is available ", Toast.LENGTH_LONG)
                    .show();
        } else {
            Toast.makeText(this, "Network not available ", Toast.LENGTH_LONG)
                    .show();
        }
        return isNetworkAvaible;
    }

    @Override
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
