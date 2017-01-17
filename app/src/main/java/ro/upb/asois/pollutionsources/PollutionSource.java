package ro.upb.asois.pollutionsources;

/**
 * Created by constantin on 1/17/2017.
 */
public class PollutionSource {
    String name;
    String type;
    double lat;
    double lng;
    String city;

    public PollutionSource(String name, String type, double lat, double lng, String city) {
        this.name = name;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.city = city;
    }

    public PollutionSource(String name, String type, double lat, double lng) {
        this.name = name;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
