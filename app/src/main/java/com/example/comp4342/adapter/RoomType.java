package com.example.comp4342.adapter;

public class RoomType {
    private int roomTypeID;
    private int hotelID;
    private String roomTypeName;
    private String description;
    private int pricePerNight;
    private int totalRooms;

    private int imageResource;

    // constructors, getters, and setters


    public RoomType(int roomTypeID, int hotelID, String roomTypeName, String description, int pricePerNight, int totalRooms, int imageResource) {
        this.roomTypeID = roomTypeID;
        this.hotelID = hotelID;
        this.roomTypeName = roomTypeName;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.totalRooms = totalRooms;
        this.imageResource = imageResource;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}