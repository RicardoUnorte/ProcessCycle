package com.example.ricardo.proyectofinal;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ricardo on 27/05/2015.
 */
public class Step {
    public int id;
    private JSONObject Jstep;
    Field[] fields;
    Decision[] dec;

    public int getId() {
        return id;
    }

    public Step(JSONObject jstep) {
        Jstep = jstep;
        try {
            id = jstep.getInt("step_id");
            getStep();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getStep() {
        try {
            String cont = Jstep.getString("content");
            JSONObject content = new JSONObject(Html.fromHtml(cont).toString());
            JSONArray field = content.getJSONArray("Fields");
            fields = new Field[field.length()];
            for(int i=0; i < field.length();i++){
                JSONObject object = field.getJSONObject(i);
                JSONArray arr;
                try {
                    arr = object.getJSONArray("possible_values");
                } catch (JSONException ex) {
                    arr = null;
                }
                fields[i] = new Field(object.getInt("id"),object.getString("caption"),object.getInt("field_type"), arr);
            }

            JSONArray decisions = content.getJSONArray("Decisions");
            dec = new Decision[decisions.length()];
            for(int i=0; i<decisions.length();i++){
                JSONObject Dec = decisions.getJSONObject(i);
                JSONArray braanches = Dec.getJSONArray("branch");
                Branch[] ba = new Branch[braanches.length()];
                try {
                    for (int j = 0; j < braanches.length(); j++) {
                        JSONObject object = braanches.getJSONObject(j);
                        ba[j] = new Branch(object.getInt("field_id"), object.getString("value"), object.getString("comparison_type"));
                    }
                } catch (JSONException ex){}
                dec[i] = new Decision(ba,Dec.getInt("go_to_step"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int NextStep(){
        for (Decision aDec : dec) {
            boolean Cumple_todo_branch = true;
            for (int j = 0; j < aDec.branches.length; j++) {
                Field field = null;
                for (Field field1 : fields) {
                    if (aDec.branches[j].id == field1.idField) {
                        field = field1;
                        break;
                    }
                }
                assert field != null;
                switch (field.type) {
                    case QUESTION:
                        String val = field.getChoosenValue().toString();
                        if (!(val.equals(aDec.branches[j].val))) {
                            Cumple_todo_branch = false;
                        }
                        break;
                    case BOOLEAM:
                        if (!(field.getChoosenValue().equals(aDec.branches[j].val))) {
                            Cumple_todo_branch = false;
                        }
                        break;

                    case NUMERIC:
                        try {
                            int valor_del_usuario = Integer.parseInt(field.getChoosenValue().toString());
                            int valor_comparar = Integer.parseInt(aDec.branches[j].val);
                            switch (aDec.branches[j].Comp) {
                                case MAYOR:
                                    if (!(valor_del_usuario > valor_comparar))
                                        Cumple_todo_branch = false;
                                    break;
                                case MENOR:
                                    if (!(valor_del_usuario < valor_comparar))
                                        Cumple_todo_branch = false;
                                    break;
                                case IGUAL:
                                    if (!(valor_del_usuario == valor_comparar))
                                        Cumple_todo_branch = false;
                                    break;
                            }
                        } catch (Exception ex) {
                            Cumple_todo_branch = false;
                        }
                        break;
                }
                if (!Cumple_todo_branch)
                    break;
            }
            if (Cumple_todo_branch) {
                return aDec.Goto;
            }
        }
        return 0;
    }

    public void SetVistas(LinearLayout linear, Context c) {
        linear.removeAllViews();
        for (Field f : fields) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = 5; params.bottomMargin = 5;
            linear.addView(f.getView(c), params);
        }
    }

}
