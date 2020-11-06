package polytech.covidalert.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name="do_test")
@Access(AccessType.FIELD)
public class DoTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long do_test_id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="test_id")
    private Test test;
    private Date test_date;
    private String test_result;



    public long getDo_test_id() {
        return do_test_id;
    }

    public void setDo_test_id(long do_test_id) {
        this.do_test_id = do_test_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Date getTest_date() {
        return test_date;
    }

    public void setTest_date(Date test_date) {
        this.test_date = test_date;
    }

    public String getTest_result() {
        return test_result;
    }

    public void setTest_result(String test_result) {
        this.test_result = test_result;
    }

}
