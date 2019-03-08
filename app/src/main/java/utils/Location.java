package utils;

import android.location.Geocoder;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class Location implements LocationListener {

    private String longitude, latitude;

    public Location(){
        longitude = "";
        latitude = "";
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        latitude = location.getLatitude()+"";
        longitude = location.getLongitude()+"";
        System.out.println("-------------");
        System.out.println("Lat: "+latitude);
        System.out.println("Long: "+longitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
