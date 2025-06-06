package com.example.coubustracker.model;

public class BusLocation {
    private String driverName;
    private String mobileNumber;
    private String locationLink;

    public BusLocation() {}

    public BusLocation(String driverName, String mobileNumber, String locationLink) {
        this.driverName = driverName;
        this.mobileNumber = mobileNumber;
        this.locationLink = locationLink;
    }

    public String getDriverName() { return driverName; }
    public String getMobileNumber() { return mobileNumber; }
    public String getLocationLink() { return locationLink; }

    public void setDriverName(String driverName) { this.driverName = driverName; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public void setLocationLink(String locationLink) { this.locationLink = locationLink; }
}
