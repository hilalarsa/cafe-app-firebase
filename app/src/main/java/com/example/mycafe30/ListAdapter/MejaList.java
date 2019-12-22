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
    //list of meja
    List<Meja> Mejas;

    public MejaList(Activity context, List<Meja> Mejas) {
        super(context, R.layout.layout_meja_list, Mejas);
        this.context = context;
        this.Mejas = Mejas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_meja_list, null, true);
        //initialize
        TextView textViewNo = (TextView) listViewItem.findViewById
                (R.id.textViewNoMeja);
        TextView textViewStatus = (TextView) listViewItem.findViewById
                (R.id.textViewStatus);
        //getting mejar at position
        Meja Meja = Mejas.get(position);
        //set mejar name
        textViewNo.setText(Meja.getNoMeja());
        //set mejar email
        textViewStatus.setText(Meja.getStatus());
        return listViewItem;
    }

}
