package polytech.covidalert.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import polytech.covidalert.kafka.KafkaPairOfCloseUsers;

import java.util.ArrayList;
import java.util.HashSet;

@RestController
@CrossOrigin
@RequestMapping("/covid/declaration")
public final class CovidDeclarationController {
    private static HashSet<KafkaPairOfCloseUsers> pairsOfCloseUsers;

    public static HashSet<KafkaPairOfCloseUsers> getPairsOfCloseUsers() {
        return pairsOfCloseUsers;
    }
    public static void addPairsOfCloseUsers(ArrayList<KafkaPairOfCloseUsers> nextPairsOfCloseUsers) {
        if (pairsOfCloseUsers == null){
            pairsOfCloseUsers = new HashSet<KafkaPairOfCloseUsers>();
        }
        for (KafkaPairOfCloseUsers pair : nextPairsOfCloseUsers) {
            pairsOfCloseUsers.add(pair);
        }
    }

    public static HashSet<String> findCloseUsersOfUser(String userEmail){
        //We use a HashSet to avoid duplicates
        HashSet<String> closeUsers = new HashSet<>();
        for (KafkaPairOfCloseUsers pair : pairsOfCloseUsers){
            String e1 = pair.getUserEmail1();
            String e2 = pair.getUserEmail2();
            if (userEmail.equals(e1)){
                closeUsers.add(e2);
            } else if (userEmail.equals(e2)){
                closeUsers.add(e1);
            }
        }

        return closeUsers;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public HashSet<String> sendAlertToCloseUsersOfUser(@RequestBody final String userEmail) {
        System.out.println("Looking for contact cases of " + userEmail + "...");
        HashSet<String> closeUsersOfUser = findCloseUsersOfUser(userEmail);
        System.out.println("Users found: " + closeUsersOfUser);
        return closeUsersOfUser;
    }

    /*
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Object sendAlertToCloseUsersOfUser(@RequestBody final String userEmail) {
        ArrayList<Map> toReturn = new ArrayList<>();
        System.out.println("Looking for contact cases of " + userEmail + "...");
        HashSet<String> closeUsersOfUser = findCloseUsersOfUser(userEmail);
        System.out.println("Users found: " + closeUsersOfUser);
        for(String email : closeUsersOfUser){
            toReturn.add(Collections.singletonMap("userEmail", email));
        }
        return toReturn;
    }
     */
}
