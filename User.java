import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.lang.*;

public class User {
    public boolean public_access;
    private URL profilePic;
    public String username;
    private String password;
    // Credit Card Number
    private String ccNum;
    private Dictionary<String,Asset> assets;
    private Dictionary<String,Subscription> subs;
    private Dictionary<String,User> network;

    public User(String name, String pwd, String cc, boolean access) {
        this.username = name;
        this.password = pwd;
        this.ccNum = cc;
        this.public_access = access;
        this.assets = new Dictionary<String,Asset>();
        this.subs = new Dictionary<String,Subscription>();
        this.network = new Dictionary<String,User>();
    }

    public boolean verifyPassword(password_entry) {
        return ( password_entry == this.password );
    }

    public void addAsset(String id) {
        Asset newAsset = new Asset(id);
        this.assets.put(id, newAsset);
    }

    public void addSub(String id, boolean paid, int numMonths) {
        Subscription newSub = new Subscription(id, paid, numMonths);
        this.subs.put(id, newSub);
    }

    public void addConnection(String username) {

    }

    // Sets path to profile picture
    public void setProfilePicture(String src) {
        this.profilePic = new URL(src);
    }
    // returns image
    public Image getProfilePicture() {
        ImageIcon profile = new ImageIcon(this.profilePic);
        return profile.getImage();
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
            extendSub();
        }
    }

    public void extendSub() {
        System.out.println("Would you like to extend your subscription?");

    }

}

public static User addUser() {
    Scanner input = new Scanner(System.in);

    // Requesting username
    System.out.println("Enter your desired username: ");
    // Add method to check if username is already taken
    String name = input.next();

    // Requesting password
    System.out.println("Enter your desired password: ");
    String pwd = input.next();

    // Verify password
    do {
        System.out.println("Please re-enter your password: ");
    } while ( !pwd.equals(input.next()) ) {
        System.out.println("Password incorrect.");
        System.out.println("Please re-enter your password: ");
    }

    // Request credit card number
    String cc = "";
    System.out.println("Would you like to save your credit card information? (y/n)");
    String tmp = input.next();
    while(true) {
        // The user wants to save their credit card info
        if (Character.toLowerCase(tmp[0]) == 'y') {
            System.out.println("Please enter your credit card number: ");
            cc = input.next();
            // Verify credit card number is valid; if valid..
            cc = tmp;
            // If not valid, request credit card number again
            break;
        }
        // The user does not want to save their credit card info
        else if (Character.toLowerCase(tmp[0]) == 'n') {
            break;

        } else {
            System.out.println("Please enter [y]es or [n]o.");
            tmp = input.next();
        }
    }

    // Request for desired profile accessibility
    boolean access = false;
    System.out.println("Would you like your account to be publicly accessible? (y/n)");
    String tmp = input.next();
    while(true) {
        // The user wants the account to be publicly accessible
        if (Character.toLowerCase(tmp[0]) == 'y') {
            access = true;
            break;
        }
        // The user does not want the account to be publicly accessible
        else if (Character.toLowerCase(tmp[0]) == 'n') {
            break;

        } else {
            System.out.println("Please enter [y]es or [n]o.");
            tmp = input.next();
        }
    }

    // Request for profile picture
    String src = "";
    System.out.println("Would you like to set your profile picture? (y/n)");
    String tmp = input.next();
    while(true) {
        // The user wants to set their profile picture
        if (Character.toLowerCase(tmp[0]) == 'y') {
            System.out.println("Please enter the path to your profile picture: ");
            src = input.next();
            // Verify if path is valid and to a valid picture format; if valid, break
            // If not valid, request path again
            break;
        }
        // The user does not want to set their profile photo
        else if (Character.toLowerCase(tmp[0]) == 'n') {
            break;

        } else {
            System.out.println("Please enter [y]es or [n]o.");
            tmp = input.next();
        }
    }


    User newUser = new User(name, pwd, cc, access);
    newUser.setProfilePicture(src);

    return newUser;
}

public static void addSub(User user) {
    Scanner input = new Scanner(System.in);
    // Requesting subscription ID
    System.out.println("Enter the subscription ID: ");
    String tmpId = input.next();
    // Add method to verify if a subscription with this ID exists
    // while( !exists(tmpId) ) {
    //    System.out.println("No subscription with this ID exists.");
    //    System.out.println("Enter a valid subscription ID: ");
    //    tmpId = input.next();
    // }
    //
    // Add method to verify if the user has already...
    //      paid for this subscription, and it is not expired:
    //          notify of current subscription, and determine if extension is desired
    //      paid for this subscription, but it is expired:
    //          determine if renewal is desired
    //
    // Otherwise...

    System.out.println("Would you like to sign up for the paid-version of this subscription? (y/n)");
    String tmpPaid = input.next();
    while(true) {
        // The user wants the paid version of the subscription
        if (Character.toLowerCase(tmpPaid[0]) == 'y') {
            // Ask user for number of months they want to subscribe for
            long numMonths = getMonths();
            user.addSub(id, true, numMonths);
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

public static void addAsset(User user) {
    Scanner input = new Scanner(System.in);
    // Requesting asset ID
    System.out.println("Enter the asset ID: ");
    String tmpId = input.next();
    // Add method to verify if an asset with this ID exists
    // while( !exists(tmpId) ) {
    //    System.out.println("No asset with this ID exists.");
    //    System.out.println("Enter a valid asset ID: ");
    //    tmpId = input.next();
    // }
    //
    // Add method to verify if the user has already...
    //      paid for this asset:
    //          notify of purchase
    //
    // Otherwise...

    System.out.println("Would you like to purchase this asset? (y/n)");
    String tmpPaid = input.next();
    while(true) {
        // The user wants to purchase the asset
        if (Character.toLowerCase(tmpPaid[0]) == 'y') {
            user.addAsset(id);
            System.out.println("Thank you for your purchase!");
            break;
        }
        // The user does not want to buy the asset
        else if (Character.toLowerCase(tmpPaid[0]) == 'n') {
            break;

        } else {
            System.out.println("Please enter [y]es or [n]o.");
            tmpPaid = input.next();
        }
    }

}

public static void addConnection(User user) {

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

// PSEUDOCODE FOR MAIN DRIVER
//
//
//
//
