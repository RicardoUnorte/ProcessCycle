package com.example.ricardo.proyectofinal;

import android.renderscript.Sampler;

import java.util.List;

/**
 * Created by Ricardo on 28/05/2015.
 */
public class Branch {

    public int id;
    public String val;
    public enum COMPARE {IGUAL,MAYOR,MENOR};
    public COMPARE Comp;

    public Branch(int id, String val, String comp) {
        this.id = id;
        this.val = val;
        if(comp.equals("<")){
            Comp = COMPARE.MENOR;
        }

        if(comp.equals(">")){
            Comp = COMPARE.MAYOR;
        }

        if(comp.equals("=")){
            Comp = COMPARE.IGUAL;
        }
    }
}
