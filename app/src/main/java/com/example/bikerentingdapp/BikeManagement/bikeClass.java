package com.example.bikerentingdapp.BikeManagement;

public class bikeClass {
    String Reg;
    String rent;

    public String getReg() {
        return Reg;
    }

    public bikeClass(String reg, String rent) {
        Reg = reg;
        this.rent = rent;
    }

    public void setReg(String reg) {
        Reg = reg;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }
}
