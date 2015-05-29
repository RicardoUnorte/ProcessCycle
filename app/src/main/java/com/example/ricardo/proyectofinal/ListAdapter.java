package com.example.ricardo.proyectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ricardo on 19/05/2015.
 */
public class ListAdapter extends BaseAdapter {


   private  List<CatAdap> Categorias;
    LayoutInflater inflater;

    public ListAdapter(Context context, List<CatAdap> categorias) {
        inflater = LayoutInflater.from(context);
       this.Categorias = categorias;

    }

    public String getName(int position){
        return Categorias.get(position).name;
    }


    @Override
    public int getCount() {
        return Categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return Categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Categorias.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView=null;
        if(convertView ==null)
        {
        convertView = inflater.inflate(android.R.layout.simple_list_item_1,null);
        textView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView = textView;
        }else{

            textView = (TextView) convertView.findViewById(android.R.id.text1);
        }
        textView.setText(Categorias.get(position).name);

        return convertView;
    }
}
