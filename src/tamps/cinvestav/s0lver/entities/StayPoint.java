package tamps.cinvestav.s0lver.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StayPoint{
    private float latitude;
    private float longitude;
    private Date arrivalTime;
    private Date departureTime;
    private int amountFixesInvolved;

    private StayPoint(){

    }

    public static StayPoint createStayPoint(ArrayList<GpsFix> list, int i, int j) {
        int sizeOfListPortion = j - i + 1;

        if (sizeOfListPortion == 0) {
            throw new RuntimeException("List provided is empty");
        }

        double sumLat = 0.0, sumLng = 0.0;
        for (int h = i; h <= j; h++) {
            sumLat += list.get(h).getLatitude();
            sumLng += list.get(h).getLongitude();
        }

        StayPoint pointOfInterest = new StayPoint();
        pointOfInterest.setLatitude((float) (sumLat / sizeOfListPortion));
        pointOfInterest.setLongitude((float) (sumLng / sizeOfListPortion));
        pointOfInterest.setArrivalTime(new Date(list.get(i).getTimestamp().getTime()));
        pointOfInterest.setDepartureTime(new Date(list.get(j).getTimestamp().getTime()));
        pointOfInterest.setAmountFixesInvolved(sizeOfListPortion);

        return pointOfInterest;
    }

    public static StayPoint createStayPoint(double sigmaLatitude, double sigmaLongitude, Date arrivalTime, Date departureTime, int amountFixes) {
        StayPoint stayPoint = new StayPoint();
        stayPoint.setAmountFixesInvolved(amountFixes);
        stayPoint.setLatitude((float) (sigmaLatitude / amountFixes));
        stayPoint.setLongitude((float) (sigmaLongitude / amountFixes));
        stayPoint.setArrivalTime(arrivalTime);
        stayPoint.setDepartureTime(departureTime);

        return stayPoint;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public int getAmountFixesInvolved() {
        return amountFixesInvolved;
    }

    public void setAmountFixesInvolved(int amountFixesInvolved) {
        this.amountFixesInvolved = amountFixesInvolved;
    }

    public String toString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return String.format("Lat: %f, Long: %f, Arr: %s, Dep: %s, Amnt: %d", getLatitude(), getLongitude(),
                simpleDateFormat.format(getArrivalTime()), simpleDateFormat.format(getDepartureTime()),
                getAmountFixesInvolved());
    }

    public String toCSV(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return String.format("%f,%f,%s,%s,%d", getLatitude(), getLongitude(),
                simpleDateFormat.format(getArrivalTime()), simpleDateFormat.format(getDepartureTime()),
                getAmountFixesInvolved());
    }
}
