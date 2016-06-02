package com.ericphee.hkmonuments;

/**
 * Created by ERic Phee on 5/19/2016.
 */
public class MonumentsVo {

    private int id;

    private String name;

    private String regions;

    private String address;

    private Double latitude;

    private Double longitude;

    private String detail;



    private String image;


    public MonumentsVo(){

    }

    // constructor
    public MonumentsVo(int id, String name, String regions, String address, Double latitude, Double longitude, String detail ){
        this.id = id;
        this.name = name;
        this.regions = regions;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.detail = detail;
    }

    // constructor
    public MonumentsVo(String name, String regions, String address, Double latitude, Double longitude, String detail){
        this.id = id;
        this.name = name;
        this.regions = regions;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




}
