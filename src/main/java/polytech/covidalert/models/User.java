package polytech.covidalert.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity(name="users")
@Access(AccessType.FIELD)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long user_id ;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String password;

    @ManyToMany
    @JoinTable(
            name="user_locations",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="location_id")
    )
    private List<Location> locations;

    // Liste des tests effectués par les users
    @OneToMany(mappedBy = "user")
    private List<DoTest> listTest;

    // Liste des Covids positifs
    @OneToMany(mappedBy = "user")
    private List<HaveCovid> listCovided;

    /*@OneToMany(mappedBy = "user")
    private List<SendWarning> listWarningSend;

    @OneToMany(mappedBy = "user")
    private List<SendWarning> listWarningReceived;
*/
    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<DoTest> getListTest() {
        return listTest;
    }

    public void setListTest(List<DoTest> listTest) {
        this.listTest = listTest;
    }

    public List<HaveCovid> getListCovided() {
        return listCovided;
    }

    public void setListCovided(List<HaveCovid> listCovided) {
        this.listCovided = listCovided;
    }

  /*  public List<SendWarning> getListWarningSend() {
        return listWarningSend;
    }

    public void setListWarningSend(List<SendWarning> listWarningSend) {
        this.listWarningSend = listWarningSend;
    }

    public List<SendWarning> getListWarningReceived() {
        return listWarningReceived;
    }

    public void setListWarningReceived(List<SendWarning> listWarningReceived) {
        this.listWarningReceived = listWarningReceived;
    }*/

    public long getUser_id() {
        return user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
