package polytech.covidalert.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity(name="locations")
@Access(AccessType.FIELD)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long location_id ;
    private float latitude;
    private float longitude;
    private Date location_date;
    @ManyToMany(mappedBy = "locations")
    @JsonIgnore
    private List<User> users;



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

    public Date getLocation_date() {
        return location_date;
    }

    public void setLocation_date(Date location_date) {
        this.location_date = location_date;
    }

    public long getLocation_id() {
        return location_id;
    }
    public void setLocation_id(long location_id) {
        this.location_id = location_id;
    }


}






