package com.example.mycafe30.ListAdapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycafe30.Model.User;
import com.example.mycafe30.R;

import java.util.List;

public class UserList extends ArrayAdapter<User> {
    private Activity context;
    //list of users
    List<User> Users;

    public UserList(Activity context, List<User> Users) {
        super(context, R.layout.layout_user_list, Users);
        this.context = context;
        this.Users = Users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);
        //initialize
        TextView textViewName = (TextView) listViewItem.findViewById
                (R.id.textViewNamaUser);
        TextView textViewAddress = (TextView) listViewItem.findViewById
                (R.id.textViewLevel);
        //getting user at position
        User User = Users.get(position);
        //set user name
        textViewName.setText(User.getNama());
        //set user email
        textViewAddress.setText(User.getEmail());
        return listViewItem;
    }
}