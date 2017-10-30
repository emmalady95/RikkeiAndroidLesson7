package com.example.emmalady.rikkeiandroidlesson7.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emmalady.rikkeiandroidlesson7.R;
import com.example.emmalady.rikkeiandroidlesson7.activity.MainActivity;
import com.example.emmalady.rikkeiandroidlesson7.db.DataHandler;
import com.example.emmalady.rikkeiandroidlesson7.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emma Nguyen on 27/10/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder> {

    private OnItemClickListener mlistener;
    //private SparseBooleanArray itemStateArray = new SparseBooleanArray();
    //private List<Contact> chooseContact = new ArrayList<>();
    private List<Contact> userContact = new ArrayList<>();
    public CheckBox mCheckedBox;

    public interface OnItemClickListener{
        public void onItemClick(Contact contact, int postion);
        public void onLongItemClick(Contact contact, int position);
    }
    public ContactAdapter(List<Contact> userContact){
        this.userContact = userContact;
    }
    public ContactAdapter(List<Contact> userContact, OnItemClickListener listener) {
        this.userContact = userContact;
        this.mlistener = listener;
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_main, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Contact contact = userContact.get(position);
        holder.tvContactName.setText(contact.getContactName());
        holder.tvContactNumber.setText(String.valueOf(contact.getContactNumber()));

        //Item Click
        holder.bind(contact, mlistener);
    }

    @Override
    public int getItemCount() {
        return null!=userContact?userContact.size():0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public TextView tvContactName;
        public TextView tvContactNumber;

        public viewHolder(View itemView) {
            super(itemView);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvContactNumber = (TextView) itemView.findViewById(R.id.tvContactNumber);
            mCheckedBox = (CheckBox) itemView.findViewById(R.id.chbChoice);
           // mCheckedBox.setVisibility(View.GONE);
        }

        public void bind(final Contact contact, final OnItemClickListener onItemClickListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    onItemClickListener.onItemClick(contact, adapterPosition);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //mCheckedBox.setVisibility(View.VISIBLE);
                    int adapterPosition = getAdapterPosition();
                    onItemClickListener.onLongItemClick(contact, adapterPosition);
                    return true;
                }
            });
            mCheckedBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    Contact user;
//                    if (!itemStateArray.get(adapterPosition, false)) {
//                        mCheckedBox.setChecked(true);
//                        itemStateArray.put(adapterPosition, true);
//
//
//                    }else{
//                        mCheckedBox.setChecked(false);
//                        itemStateArray.put(adapterPosition, false);
//                    }
                    if (userContact.get(adapterPosition).getChecked()) {
                        mCheckedBox.setChecked(false);
                        userContact.get(adapterPosition).setChecked(false);
                        //chooseContact.remove(adapterPosition);
                    }
                    else {
                        mCheckedBox.setChecked(true);
                        userContact.get(adapterPosition).setChecked(true);
                    }
                }
            });
        }
    }
    public void deleteChooseContact(Context context){
        Contact user;
        for (int i = 0; i < userContact.size(); i++){
//            user = chooseContact.get(i);
//            int id = user.getId();
//            DataHandler dataHandler = new DataHandler(context);
//            dataHandler.deleteData(id);
            if (userContact.get(i).getChecked() == true){
                user = userContact.get(i);
                int id = user.getId();
                DataHandler dataHandler = new DataHandler(context);
                dataHandler.deleteData(id);
                Toast.makeText(context, "THÀNH CÔNG", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "KHÔNG THÀNH CÔNG", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
