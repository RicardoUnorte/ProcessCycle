package com.example.ricardo.proyectofinal;

/**
 * Created by Ricardo on 28/05/2015.
 */
public class Decision {
    public Branch[] branches;
    public int Goto;

    public Decision(Branch[] branches, int aGoto) {
        this.branches = branches;
        Goto = aGoto;
    }
}
