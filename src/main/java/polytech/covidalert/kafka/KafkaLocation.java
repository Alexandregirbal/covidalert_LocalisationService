package polytech.covidalert.kafka;

public class KafkaLocation {
    private String userEmail;
    private float longitude;
    private float latitude;
    private int timestamp;

    public KafkaLocation(){}
    public KafkaLocation(String userEmail, float longitude, float latitude, int timestamp) {
        this.userEmail = userEmail;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public int getTimestamp() { return this.timestamp; }
}
