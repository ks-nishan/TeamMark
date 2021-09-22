package com.example.teammark;

public class Model {

    String id,title,district,person, engine,fuel,mobile,land,amount,desc;

    public Model(){

    }

    public Model(String id, String title, String district, String person, String engin, String fuel, String mobile, String land, String amount, String desc) {
        this.id = id;
        this.title = title;
        this.district = district;
        this.person = person;
        this.engine = engin;
        this.fuel = fuel;
        this.mobile = mobile;
        this.land = land;
        this.amount = amount;
        this.desc = desc;
    }

    public Model(String id, String title, String district, String person, String amount,String mobile) {
        this.id = id;
        this.title = title;
        this.district = district;
        this.person = person;
        this.amount = amount;
        this.mobile = mobile;
    }

    public Model(String id, String title, String district, String person, String amount, String mobile,String engine) {
        this.id = id;
        this.title = title;
        this.district = district;
        this.person = person;
        this.amount = amount;
        this.mobile = mobile;
        this.engine = engine;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getEngin() {
        return engine;
    }

    public void setEngin(String engin) {
        this.engine = engin;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
