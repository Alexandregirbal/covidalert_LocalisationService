package polytech.covidalert.models;

import javax.persistence.*;
import java.util.List;

@Entity(name="warning")
@Access(AccessType.FIELD)
public class Warning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long warning_id;
    private String type;

   /* @OneToMany(mappedBy = "warning")
    private List<SendWarning> listWarning;

    public List<SendWarning> getListWarning() {
        return listWarning;
    }

    public void setListWarning(List<SendWarning> listWarning) {
        this.listWarning = listWarning;
    }*/

    public long getWarning_id() {
        return warning_id;
    }

    public void setWarning_id(long warning_id) {
        this.warning_id = warning_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private String label;
}
