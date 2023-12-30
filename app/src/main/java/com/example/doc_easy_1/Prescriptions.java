package com.example.doc_easy_1;

public class Prescriptions {
    private String Age;
    private String Date;
    private String DoctorName;
    private String Gender;
    private String InOrOut;
    private String PatientName;
    private String TimeSlot;
    private String prescriptionDetails;
    private String mid;
    public Prescriptions(){

    }
    public Prescriptions(String age, String date, String doctorName, String gender, String inOrOut, String patientName, String timeSlot, String prescriptionDetails) {
        this.Age = age;
        this.Date = date;
        this.DoctorName = doctorName;
        this.Gender = gender;
        this.InOrOut = inOrOut;
        this.PatientName = patientName;
        this.TimeSlot = timeSlot;
        this.prescriptionDetails = prescriptionDetails;
        this.mid=mid;
    }



    public String getPrescriptionDetails() {
        return prescriptionDetails;
    }

    public void setPrescriptionDetails(String prescriptionDetails) {
        this.prescriptionDetails = prescriptionDetails;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getInOrOut() {
        return InOrOut;
    }

    public void setInOrOut(String inOrOut) {
        InOrOut = inOrOut;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getTimeSlot() {
        return TimeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        TimeSlot = timeSlot;
    }
}
