package com.example.comp4342.adapter;

public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private int dataImage;

    private String loc;

    private int hotelID;

    private String startTime;
    private String endTime;

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataLang() {
        return dataLang;
    }

    public String getLoc() {
        return loc;
    }

    public int getHotelID() {
        return hotelID;
    }
    public int getDataImage() {
        return dataImage;
    }

    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public DataClass(String dataTitle, String dataDesc, String dataLang, int dataImage, String loc, int hotelID, String startTime, String endTime) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
        this.loc = loc;
        this.hotelID = hotelID;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
