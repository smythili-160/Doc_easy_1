package com.example.doc_easy_1;

public class Appointment {
    private String mName;
    private  int mAge;
    private String mGender;
    private String mType;
    private String mid;
    private String mRemarks;
    private String time;
    private String doctor;
    private String date;
    private String inOrOut;
    private String address;
    private String phoneNumber;
    public Appointment(){
        //Required for Firestore deserialization
    }

    public Appointment(String mName, int mAge, String mGender, String mType, String mid, String mRemarks, String time, String doctor, String date, String inOrOut, String address, String phoneNumber) {
        this.mName = mName;
        this.mAge = mAge;
        this.mGender = mGender;
        this.mType = mType;
        this.mid = mid;
        this.mRemarks = mRemarks;
        this.time = time;
        this.doctor = doctor;
        this.date = date;
        this.inOrOut = inOrOut;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int mAge) {
        this.mAge = mAge;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMRemarks() {
        return mRemarks;
    }

    public void setMRemarks(String mRemarks) {
        this.mRemarks = mRemarks;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
