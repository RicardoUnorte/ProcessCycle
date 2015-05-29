package com.example.ricardo.proyectofinal.MainVistas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardo.proyectofinal.ConnectJson;
import com.example.ricardo.proyectofinal.DownF;
import com.example.ricardo.proyectofinal.R;
import com.example.ricardo.proyectofinal.Step;
import com.example.ricardo.proyectofinal.StepFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by Ricardo on 27/05/2015.
 */
public class MainStep extends AppCompatActivity implements DownF, FragmentManager.OnBackStackChangedListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Bundle extras = getIntent().getExtras();
        String Name = extras.getString("name");
        Long id = extras.getLong("id");
        textView = (TextView) findViewById(R.id.step);
        textView.setText(Name);
        ConnectJson step = new ConnectJson(this);
        step.execute("https://dynamicformapi.herokuapp.com/steps/by_procedure/"+id+".json");
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void OnFinish(String obj) {
        if (obj != "" && obj != null) {
            try {
                JSONArray steps_json = new JSONArray(obj);
                if (steps_json.length() != 0) {
                    steps = new Step[steps_json.length()];
                    for (int i = 0; i < steps.length; i++) {
                        steps[i] = new Step(steps_json.getJSONObject(i));
                    }
                    StepFragment frag = new StepFragment();
                    frag.SetStep(findStepById(1));
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.scroll_step, frag)
                            .commit();
                    current_frag = frag;
                } else {
                    finish();
                }
            } catch (JSONException e) {
                this.finish();
            }
        }
    }

    private void SetStep(int step_id) {
        StepFragment frag= new StepFragment();
        frag.SetStep(findStepById(step_id));
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.scroll_step, frag)
                .commit();
        current_frag = frag;
    }

    public void NextStep(View v) {
        next_step = current_frag.step.NextStep();
        if (next_step == 0) {
            Toast.makeText(this, "No hay paso siguiente para las condiciones dadas", Toast.LENGTH_SHORT).show();
        } else if (next_step == -1) {
            finish();
        } else {
            SetStep(next_step);
        }
    }

    private Step findStepById(int id) {
        for (Step s : steps) {
            if (s.id == id)
                return s;
        }
        return null;
    }

    public void SetNextStep(int step_id) {
        next_step = step_id;
    }


    Step steps[];
    private int next_step;

    @Override
    public void onBackStackChanged() {
        current_frag = (StepFragment) getVisibleFragment();
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }
    private StepFragment current_frag;
}
