import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.lang.*;

public class User {
    public String username;
    private String password;
    // Credit Card Number
    private String ccNum;
    private List<Asset> assets;
    private List<Subscription> subs;

    public User() {
        this.username = "";
        this.password = "";
        this.ccNum = "";
        this.assets = new List<Asset>();
        this.subs = new List<Subscription>();
    }

    public boolean verifyPassword(password_entry) {
        return ( password_entry == this.password );
    }

    public void addAsset(String id) {
        // Add method to verify if an asset with this ID exists
        Asset newAsset = new Asset(id);
        this.assets.add(newAsset);

    }

    public void addSub(String id, boolean paid, int numMonths) {
        Subscription newSub = new Subscription(id, paid, numMonths);
        this.subs.add(newSub);
    }
}

public class Asset {
    public String asset_id;

    public Asset(String id) {
        this.asset_id = id;
    }
}

public class Subscription {
    public String sub_id;
    private boolean paidSub;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Subscription(String id, boolean paid, int numMonths) {
        this.sub_id = id;
        this.paidSub = paid;
        this.startDate = LocalDateTime.now();
        this.endDate = this.startDate.plusMonths(numMonths);
    }

    public boolean isPaid() {
        return paidSub;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.endDate);
    }

    public long getRemainder() {
        if !(this.isExpired()) {
            return DAYS.between(LocalDateTime.now(), this.endDate);
        }
        else {
            return 0;
        }
    }

    public void renewSub() {
        if ( this.isExpired() ) {
            System.out.printf("Your subcription expired %l days ago, would you like to renew it? (y/n)\n",
                    DAYS.between(this.endDate, LocalDateTime.now()));
            String tmpRenew = input.next();
            while(true) {
                // The user wants to renew the subscription
                if (Character.toLowerCase(tmpRenew[0]) == 'y') {
                    // Ask user for number of months they want to subscribe for
                    long numMonths = getMonths();
                    this.startDate = LocalDateTime.now();
                    this.endDate = this.startDate.plusMonths(numMonths);
                    //charge user for the number of months they subscribed for
                    break;
                }
                // The user does not want to renew the subscription
                else if (Character.toLowerCase(tmpRenew[0]) == 'n') {
                    this.paidSub = false;
                    System.out.println("Your subscription has not been renewed. However, you may still" +
                            "access the free version!");
                    break;

                } else {
                    System.out.println("Please enter [y]es or [n]o.");
                    tmpRenew = input.next();
                }
            }
        } else {
            System.out.println("Your subcription has not yet expired!");
            System.out.printf("There are still %l days remaining of your subscription.\n", this.getRemainder() );

        }
    }

}

public static User addUser() {
    Scanner input = new Scanner(System.in);
    User newUser = new User();

    // Requesting username
    System.out.println("Enter your desired username: ");
    // Add method to check if username is already taken
    newUser.username = input.next();

    // Requesting password
    System.out.println("Enter your desired password: ");
    String tmpPassword = input.next();

    // Verify password
    do {
        System.out.println("Please re-enter your password: ");
    } while ( tmpPassword != input.next() ) {
        System.out.println("Password incorrect.");
        System.out.println("Please re-enter your password: ");
    }
    // Set user password
    newUser.password = tmpPassword;

    return newUser;

}

public static void addSub(User user) {
    Scanner input = new Scanner(System.in);
    // Requesting username
    System.out.println("Enter the subscription ID: ");
    String tmpId = input.next();
    // Add method to verify if a subscription with this ID exists
    // while( !exists(tmpId) ) {
    //    System.out.println("No subscription with this ID exists.");
    //    System.out.println("Enter a valid subscription ID: ");
    //    tmpId = input.next();
    // }
    System.out.println("Would you like to sign up for the paid-version of this subscription? (y/n)");
    String tmpPaid = input.next();
    while(true) {
        // The user wants the paid version of the subscription
        if (Character.toLowerCase(tmpPaid[0]) == 'y') {
            // Ask user for number of months they want to subscribe for
            long numMonths = getMonths();
            user.addSub(id, numMonths);
            break;
        }
        // The user does not want the paid version of the subscription
        else if (Character.toLowerCase(tmpPaid[0]) == 'n') {
            user.addSub(id, false, 0);
            break;

        } else {
            System.out.println("Please enter [y]es or [n]o.");
            tmpPaid = input.next();
        }
    }

}

public static long getMonths() {
    System.out.println("How many months would you like to subscribe for?");
    while(true) {
        String tmp = input.next();
        try {
            long tmpMonths = Long.parseLong(tmp);
            break;
        } catch (NumberFormatException | NullPointerException nfe) {
            System.out.println("Please enter a valid number of months.");
        }

    }
    return tmpMonths;
}



