package com.example.ricardo.proyectofinal;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ricardo.proyectofinal.MainVistas.MainStep;

/**
 * Created by Ricardo on 27/05/2015.
 */
public class StepFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        linear = new LinearLayout(inflater.getContext());
        linear.setOrientation(LinearLayout.VERTICAL);
        step.SetVistas(linear, this.getActivity());
        return linear;
    }

    public void SetStep(Step step) {
        this.step = step;
    }

    public Step step;
    private LinearLayout linear;
}
