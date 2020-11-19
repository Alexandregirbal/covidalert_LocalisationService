package polytech.covidalert.kafka;

import java.util.ArrayList;

public class KafkaPairOfCloseUsers {
    private String userEmail1;
    private String userEmail2;

    public String getUserEmail1() {
        return userEmail1;
    }

    public String getUserEmail2() {
        return userEmail2;
    }

    public KafkaPairOfCloseUsers(String userEmail1, String userEmail2) {
        this.userEmail1 = userEmail1;
        this.userEmail2 = userEmail2;
    }

    public static Boolean areTwoPairsSimilars(KafkaPairOfCloseUsers p1, KafkaPairOfCloseUsers p2) {
        String e11 = p1.getUserEmail1();
        String e12 = p1.getUserEmail2();
        String e21 = p2.getUserEmail1();
        String e22 = p2.getUserEmail2();
        return ((e11 != e21 && e12 != e22) || (e11 != e22 && e12 != e21) );
    }

    @Override
    public String toString() {
        return "KafkaPairOfCloseUsers: " + userEmail1 + '/' + userEmail2;
    }
}
