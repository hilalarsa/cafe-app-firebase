package com.example.mycafe30.ListAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mycafe30.Model.Meja;

import com.example.mycafe30.Model.User;
import com.example.mycafe30.R;

import java.util.List;

public class MejaList extends ArrayAdapter {

    private Activity context;
    //list of users
    List<Meja> Mejas;

    public MejaList(Activity context, List<Meja> Mejas) {
        super(context, R.layout.layout_meja_list, Mejas);
        this.context = context;
        this.Mejas = Mejas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById
                (R.id.textViewNamaMeja);
        TextView textViewAddress = (TextView) listViewItem.findViewById
                (R.id.textViewLevel);
        //getting user at position
        Meja Meja = Mejas.get(position);
        //set user name
        textViewName.setText(Meja.getNama());
        //set user email
        textViewAddress.setText(Meja.getEmail());
        return listViewItem;
    }
}
