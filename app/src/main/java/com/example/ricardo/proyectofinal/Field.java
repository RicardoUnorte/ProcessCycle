package com.example.ricardo.proyectofinal;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Ricardo on 27/05/2015.
 */
public class Field {
    public int idField;
    public enum TYPE {QUESTION,BOOLEAM,NUMERIC,LABEL};
    public TYPE type;
    public String caption;
    public JSONArray possible_values;
    private View v;

    public Field(int idField, String caption, int type, JSONArray PV) {
        this.idField = idField;
        this.caption = caption;
        this.type = TYPE.values()[type];
        this.possible_values = PV;

    }

    public View getView(Context context){
        switch(type){

            case QUESTION:
                v = View.inflate(context,R.layout.view_question,null);
                TextView capQ = (TextView) v.findViewById(R.id.questCap);
                capQ.setText(caption);
                RadioGroup rg = (RadioGroup) v.findViewById(R.id.Qrg);
                for(int i=0;i<possible_values.length();i++){
                    RadioButton r = new RadioButton(v.getContext());
                    if (i == 0) {
                        r.setChecked(true);
                    }
                    r.setId(i);
                    try {
                        r.setText(possible_values.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rg.addView(r);
                }
                break;

            case BOOLEAM:
                v = View.inflate(context,R.layout.view_boolean,null);
                TextView capB = (TextView) v.findViewById(R.id.BoolCap);
                RadioButton rb = (RadioButton)v.findViewById(R.id.sound);
                rb.setChecked(true);
                capB.setText(caption);
                break;

            case NUMERIC:
                v = View.inflate(context,R.layout.view_numeric,null);
                TextView capN = (TextView) v.findViewById(R.id.numCap);
                capN.setText(caption);
                break;

            case LABEL:
                v = View.inflate(context,R.layout.view_label,null);
                TextView capL = (TextView) v.findViewById(R.id.labelText);
                capL.setText(caption);

                break;

        }
        return v;
    }

    public Object getChoosenValue() {
        switch(type){

            case QUESTION:
                RadioGroup gro = (RadioGroup) v.findViewById(R.id.Qrg);
                if(gro.getCheckedRadioButtonId()!=-1){
                    int id= gro.getCheckedRadioButtonId();
                    View radioButton = gro.findViewById(id);
                    int radioId = gro.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) gro.getChildAt(radioId);
                    String selection = (String) btn.getText();
                    return selection;
                } else {
                    return "";
                }

            case BOOLEAM:
                RadioGroup groBool = (RadioGroup) v.findViewById(R.id.Qrr);
                if(groBool.getCheckedRadioButtonId()!=-1) {
                    int id = groBool.getCheckedRadioButtonId();
                    View radioButton = groBool.findViewById(id);
                    int radioId = groBool.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) groBool.getChildAt(radioId);
                    String selection = (String) btn.getText();
                    return selection;
                }
                break;

            case NUMERIC:
                EditText numT = (EditText) v.findViewById(R.id.numberText);
                String num = numT.getText().toString();
                return num;


        }
            return null;
    }
}
