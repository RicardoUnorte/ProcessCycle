package com.example.ricardo.proyectofinal;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Laboratorio on 25/05/2015.
 */
public class ProcAdap extends BaseAdapter {

   private List<Process> procesos;
    private LayoutInflater inflate;

    public ProcAdap(List<Process> procesos, Context context) {
        this.procesos = procesos;
        inflate = LayoutInflater.from(context);
    }


    public String getName(int position){
        return procesos.get(position).name;
    }
    @Override
    public int getCount() {
        return procesos.size();
    }

    @Override
    public Object getItem(int position) {
        return procesos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return procesos.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView=null;
        if(convertView ==null)
        {
            convertView = inflate.inflate(android.R.layout.simple_list_item_1,null);
            textView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView = textView;
        }else{

            textView = (TextView) convertView.findViewById(android.R.id.text1);
        }
        textView.setText(procesos.get(position).name);

        return convertView;
    }
}
