package polytech.covidalert.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name="have_covid")
@Access(AccessType.FIELD)
public class HaveCovid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long have_covid_id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="covid_id")
    private Covid covid;
    private Date covid_date_begin;
    private int covid_duration;



    public long getHave_covid_id() {
        return have_covid_id;
    }

    public void setHave_covid_id(long have_covid_id) {
        this.have_covid_id = have_covid_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Covid getCovid() {
        return covid;
    }

    public void setCovid(Covid covid) {
        this.covid = covid;
    }

    public Date getCovid_date_begin() {
        return covid_date_begin;
    }

    public void setCovid_date_begin(Date covid_date_begin) {
        this.covid_date_begin = covid_date_begin;
    }

    public int getCovid_duration() {
        return covid_duration;
    }

    public void setCovid_duration(int covid_duration) {
        this.covid_duration = covid_duration;
    }
}
