package com.example.mycafe30.ListAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycafe30.Model.Menu;
import com.example.mycafe30.Model.User;
import com.example.mycafe30.R;

import java.util.List;

public class MenuList extends ArrayAdapter<Menu> {
    private Activity context;
    //list of users
    List<Menu> Menus;

    public MenuList(Activity context, List<Menu> Menus) {
        super(context, R.layout.layout_menu_list, Menus);
        this.context = context;
        this.Menus = Menus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_menu_list, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById
                (R.id.textViewNamaMenu);
        TextView textViewAddress = (TextView) listViewItem.findViewById
                (R.id.textViewHarga);
        //getting user at position
        Menu Menu = Menus.get(position);
        //set user name
        textViewName.setText(Menu.getNamaMenu());
        //set user email
        textViewAddress.setText(Menu.getHarga());
        return listViewItem;
    }
}