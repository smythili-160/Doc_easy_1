package com.example.doc_easy_1;

public class Leave {
    private String doctor_user_name;

    private String leave_end_date;
    private String leave_start_date;
    private String remarks;
    public String getLeave_end_date() {
        return leave_end_date;
    }

    public void setLeave_end_date(String leave_end_date) {
        this.leave_end_date = leave_end_date;
    }

    public String getLeave_start_date() {
        return leave_start_date;
    }

    public void setLeave_start_date(String leave_start_date) {
        this.leave_start_date = leave_start_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getDoctor_user_name() {
        return doctor_user_name;
    }

    public void setDoctor_user_name(String doctor_user_name) {
        this.doctor_user_name = doctor_user_name;
    }
}
