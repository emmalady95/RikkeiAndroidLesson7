package com.example.emmalady.rikkeiandroidlesson7.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emmalady.rikkeiandroidlesson7.R;
import com.example.emmalady.rikkeiandroidlesson7.adapter.ContactAdapter;
import com.example.emmalady.rikkeiandroidlesson7.db.DataHandler;
import com.example.emmalady.rikkeiandroidlesson7.model.Contact;
import com.example.emmalady.rikkeiandroidlesson7.model.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private List<Contact> contactList = new ArrayList<>();

    //BUTTON
    private Button btAdd;
    private Button btSwitch;
    private Button btCancel;
    private Button btAdSave;
    private Button btClose;
    private Button btSdSave;
    private Button btDelete;
    private Button btDeleteDialog;

    //EDITTEXT
    private EditText etContactName;
    private EditText etContactNumber;
    private EditText etContactNameShow;
    private EditText etContactNumberShow;

    //TEXTVIEW
    private TextView tvID;

    //APP COMPAT
    private RecyclerView mRcContact;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIdActivity();
        btDelete.setVisibility(View.GONE);
        btCancel.setVisibility(View.GONE);

        //Get All Contact
        contactList = new DataHandler(this).getAllData();

       //Set Content For Recycler View
        final ContactAdapter contactAdapter = new ContactAdapter(contactList);
        //contactAdapter.mCheckedBox.setVisibility(View.GONE);
        mRcContact.setAdapter(new ContactAdapter(contactList, new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact, int position) {
                showDialog(R.layout.item_show_dialog, "SHOW CONTACT");
                findViewByIdShowDialog();
                contact = contactList.get(position);
                tvID.setText(String.valueOf(contact.getId()));
                etContactNameShow.setText(contact.getContactName());
                etContactNumberShow.setText(String.valueOf(contact.getContactNumber()));

                final int id = contact.getId();
                btDeleteDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteData deleteData = new DeleteData(id);
                        deleteData.execute();
                    }
                });
                btSdSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etContactNameShow.getText().toString();
                        int number = Integer.parseInt(etContactNumberShow.getText().toString());
                        UpdateData updateData = new UpdateData(id, name, number);
                        updateData.execute();
                    }
                });
            }

            @Override
            public void onLongItemClick(Contact contact, int position) {
                btDelete.setVisibility(View.VISIBLE);
                btCancel.setVisibility(View.VISIBLE);
                //contactAdapter.mCheckedBox.setVisibility(View.VISIBLE);
            }
        }));
        mRcContact.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //On Click
        //Add Dialog
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Show Dialog
                showDialog(R.layout.item_add_dialog, "ADD CONTACT");

                //Find View By ID
                findViewByIdAddDialog();

                //Add Data From EditText To Database (Set On Click For Save Button In Add Dialog)
                btAdSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etContactName.getText().toString();
                        int number = Integer.parseInt(etContactNumber.getText().toString());
                        //addMoreContact(name, number);
                        InsertData insertData = new InsertData(name, number);
                        insertData.execute();
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);

                    }
                });

                //Close Dialog (Set On Click For Close Button In Add Dialog)
                btClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        btSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactAdapter contactAdapter = new ContactAdapter(contactList);
                mRcContact.setAdapter(contactAdapter);
                mRcContact.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, LinearLayoutManager.VERTICAL,false));
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactAdapter.deleteChooseContact(MainActivity.this);
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btDelete.setVisibility(View.GONE);
                btCancel.setVisibility(View.GONE);
//                ContactAdapter contactAdapter = new ContactAdapter(contactList);
//                contactAdapter.mCheckedBox.setVisibility(View.GONE);
            }
        });
    }

    //Find View By ID
    public void findViewByIdActivity(){
        btAdd = (Button) findViewById(R.id.btAdd);
        btSwitch = (Button) findViewById(R.id.btSwitch);
        btDelete = (Button) findViewById(R.id.btDelete);
        btCancel= (Button) findViewById(R.id.btCancel);
        mRcContact = (RecyclerView) findViewById(R.id.rc_Content);
    }
    public void findViewByIdAddDialog(){
        btAdSave = (Button) dialog.findViewById(R.id.btSave);
        btClose = (Button) dialog.findViewById(R.id.btClose);
        etContactName = (EditText) dialog.findViewById(R.id.etContactName);
        etContactNumber = (EditText) dialog.findViewById(R.id.etContactNumber);

    }
    public void findViewByIdShowDialog(){
        btSdSave = (Button) dialog.findViewById(R.id.btSave);
        btDeleteDialog = (Button) dialog.findViewById(R.id.btDelete);
        etContactNameShow = (EditText) dialog.findViewById(R.id.etContactName);
        etContactNumberShow = (EditText) dialog.findViewById(R.id.etContactNumber);
        tvID = (TextView) dialog.findViewById(R.id.tvID);
    }

    //Show Dialog
    public void showDialog(int resLayoutID, String title){
        //Create Dialog
        dialog = new Dialog(MainActivity.this);
        //Set layout for Dialog
        dialog.setContentView(resLayoutID);
        //Set title for Dialog
        dialog.setTitle(title);
        //Show Dialog
        dialog.show();
    }

    //Add Contact
//    public void addMoreContact(String contactName, int contactNumber){
//        DataHandler dataHandler = new DataHandler(this);
//        List<Contact> contactList = new ArrayList<>();
//        contactList.add(new Contact(contactName, contactNumber));
//        int pos = (contactList.size()) - 1;
//        boolean kt = dataHandler.insertData(contactList.get(pos));
//        if (kt == false) {
//            Toast.makeText(this,"THÊM KHÔNG THÀNH CÔNG", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this,"THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
//        }
//    }

    //Async Task
    //INSERT
    public class InsertData extends AsyncTask<Void, Void, Void>{

        String contactName;
        int contactNumber;
        public InsertData(String contactName, int contactNumber){
            this.contactName = contactName;
            this.contactNumber = contactNumber;
        }
        @Override
        protected void onPreExecute() {

            System.out.println("Inside onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            DataHandler dataHandler = new DataHandler(MainActivity.this);
            List<Contact> contactList = new ArrayList<>();
            contactList.add(new Contact(contactName, contactNumber));
            int pos = (contactList.size()) - 1;
            boolean kt = dataHandler.insertData(contactList.get(pos));
            if (kt == false) {
                Toast.makeText(MainActivity.this,"THÊM KHÔNG THÀNH CÔNG", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            System.out.println("Inside onPostExecute");

        }
    }

    //DELETE
    public class DeleteData extends AsyncTask<Void, Void, Void>{

        int id;
        public DeleteData(int id){
           this.id = id;
        }
        @Override
        protected void onPreExecute() {

            System.out.println("Inside onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            DataHandler dataHandler = new DataHandler(MainActivity.this);
            dataHandler.deleteData(id);
            Toast.makeText(MainActivity.this,"XÓA THÀNH CÔNG", Toast.LENGTH_SHORT).show();
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            System.out.println("Inside onPostExecute");

        }
    }

    //UPDATE
    public class UpdateData extends AsyncTask<Void, Void, Void>{

        int id;
        String name;
        int number;

        public UpdateData(int id, String name, int number){
            this.id = id;
            this.name = name;
            this.number = number;
        }
        @Override
        protected void onPreExecute() {

            System.out.println("Inside onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            DataHandler dataHandler = new DataHandler(MainActivity.this);
            dataHandler.updateData(id, name, number);
            Toast.makeText(MainActivity.this,"SỬA THÀNH CÔNG", Toast.LENGTH_SHORT).show();
            return null;
        }
        @Override
        protected void onPostExecute(Void res) {
            System.out.println("Inside onPostExecute");

        }
    }
}
