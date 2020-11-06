package polytech.covidalert.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name="test")
@Access(AccessType.FIELD)
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long test_id;
    private String type;
    private String label;

    // Liste des tests effectués par les users
    @OneToMany(mappedBy = "test")
    private List<DoTest> listTest;



    public long getTest_id() {
        return test_id;
    }

    public void setTest_id(long test_id) {
        this.test_id = test_id;
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
}
