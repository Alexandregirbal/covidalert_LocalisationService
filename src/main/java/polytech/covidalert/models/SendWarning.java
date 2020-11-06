/*package polytech.covidalert.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name="send_warning")
@Access(AccessType.FIELD)
public class SendWarning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long send_warning_id;
    @ManyToOne
    @JoinColumn(name="user_id_infected")
    private User user_infected;
    @ManyToOne
    @JoinColumn(name="user_id_contacted")
    private User user_contacted;
    @ManyToOne
    @JoinColumn(name="warning_id")
    private Warning warning;
    private Date warning_date;

    public long getSend_warning_id() {
        return send_warning_id;
    }

    public void setSend_warning_id(long send_warning_id) {
        this.send_warning_id = send_warning_id;
    }

    public User getUser_infected() {
        return user_infected;
    }

    public void setUser_infected(User user_infected) {
        this.user_infected = user_infected;
    }

    public User getUser_contacted() {
        return user_contacted;
    }

    public void setUser_contacted(User user_contacted) {
        this.user_contacted = user_contacted;
    }

    public Warning getWarning() {
        return warning;
    }

    public void setWarning(Warning warning) {
        this.warning = warning;
    }

    public Date getWarning_date() {
        return warning_date;
    }

    public void setWarning_date(Date warning_date) {
        this.warning_date = warning_date;
    }
}*/
