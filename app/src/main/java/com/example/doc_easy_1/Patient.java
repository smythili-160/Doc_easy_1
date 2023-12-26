package com.example.doc_easy_1;

public class Patient {
    String name;
    String phoneNumber;
    String address;
    String date;
    String gender;
    String inOrOut;
    String mid;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    int age;
    public Patient(){
        //Required for Firestore deserialization
    }

    public Patient(String name, String phoneNumber, String address, String date, String gender, String inOrOut, int age) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.date = date;
        this.gender = gender;
        this.inOrOut = inOrOut;
        this.age = age;
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }
}
