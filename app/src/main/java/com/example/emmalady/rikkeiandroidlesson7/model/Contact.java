package com.example.emmalady.rikkeiandroidlesson7.model;

/**
 * Created by Emma Nguyen on 27/10/2017.
 */

public class Contact {
    private int id;
    private String contactName;
    private int contactNumber;
    private boolean isChecked;

    public Contact (int id, String contactName, int contactNumber){
        this.id = id;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }
    public Contact (String contactName, int contactNumber){
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
