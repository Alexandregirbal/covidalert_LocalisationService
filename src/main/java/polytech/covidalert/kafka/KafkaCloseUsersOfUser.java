package polytech.covidalert.kafka;

import java.util.ArrayList;

public class KafkaCloseUsersOfUser {
    private String userEmail;
    private ArrayList<String> closeUsersEmails = new ArrayList<>();

    public KafkaCloseUsersOfUser(String userEmail, String closeUsersEmails) {
        this.userEmail = userEmail;
        this.closeUsersEmails.add(closeUsersEmails);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ArrayList<String> getCloseUsersEmails() {
        return closeUsersEmails;
    }

    public void setCloseUsersEmails(ArrayList<String> closeUsersEmails) {
        this.closeUsersEmails = closeUsersEmails;
    }
}
