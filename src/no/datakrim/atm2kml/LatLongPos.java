package no.datakrim.atm2kml;


public class LatLongPos {
    private String latitude;
    private String longitude;

    public LatLongPos(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
