package polytech.covidalert.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity(name="user_locations")
@Access(AccessType.FIELD)
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long user_location_id ;
    private long user_id;
    private long location_id;
    private Date user_location_date;

    public long getUser_location_id() {
        return user_location_id;
    }

    public void setUser_location_id(long user_location_id) {
        this.user_location_id = user_location_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(long location_id) {
        this.location_id = location_id;
    }

    public Date getUser_location_date() {
        return user_location_date;
    }

    public void setUser_location_date(Date user_location_date) {
        this.user_location_date = user_location_date;
    }
}






