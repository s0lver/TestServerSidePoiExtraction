package tamps.cinvestav.s0lver.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class GpsFix{
    private double latitude;
    private double longitude;
    private Date timestamp;

    public GpsFix(double latitude, double longitude, Date timestamp) {
        this.timestamp = timestamp;
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return String.format(
                "Lat: %f, Long: %f, ts: %s",
                getLatitude(),
                getLongitude(),
                sdf.format(getTimestamp())
        );
    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return new StringBuilder()
                .append(getLatitude())
                .append(',')
                .append(getLongitude())
                .append(',')
                .append(sdf.format(getTimestamp()))
                .toString();
    }
}
