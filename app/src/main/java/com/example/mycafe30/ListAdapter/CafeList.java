package com.example.mycafe30.ListAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mycafe30.Model.Cafe;
import com.example.mycafe30.Model.Meja;
import com.example.mycafe30.R;

import java.util.List;

public class CafeList extends ArrayAdapter {
    private Activity context;
    //list of meja
    List<Cafe> Cafes;

    public CafeList(Activity context, List<Cafe> Cafes) {
        super(context, R.layout.layout_cafe_list, Cafes);
        this.context = context;
        this.Cafes = Cafes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_cafe_list, null, true);
        //initialize
        TextView textViewNamaCafe = (TextView) listViewItem.findViewById
                (R.id.textViewNamaCafe);
        TextView textViewLokasi = (TextView) listViewItem.findViewById
                (R.id.textViewLokasi);
        //getting mejar at position
        Cafe Cafe = Cafes.get(position);
        //set mejar name
        textViewNamaCafe.setText(Cafe.getNamaCafe());
        //set mejar email
        textViewLokasi.setText(Cafe.getLokasi());
        return listViewItem;
    }
}
