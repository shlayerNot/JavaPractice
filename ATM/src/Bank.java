import java.util.ArrayList;
import java.util.Random;

public class Bank {
    
    private String name;

    private ArrayList<User> user;

    private ArrayList<Account> account;

    public Bank(String name){
        this.name = name;
        this.user = new ArrayList<User>();
        this.account = new ArrayList<Account>();
    }
    public String getNewUserUUID(){
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        //continue looping until we get the unique id
        do{
            //generate the number
            uuid = "";
            for (int c = 0; c<len; c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for (User u : this.user){
                if (uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);


        return uuid;
    }

    public String getNewAccountUUID(){
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        //continue looping until we get the unique id
        do{
            //generate the number
            uuid = "";
            for (int c = 0; c<len; c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for (Account a : this.account){
                if (uuid.compareTo(a.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);


        return uuid;
    }


    public User addUser(String firstName, String lastName, String pin){
        User newUser = new User(firstName, lastName, pin, this);
        this.user.add(newUser);

        //CReate a savings account
        Account newAccount = new Account("Savings", newUser, this);
        //add to holder and bank list
        newUser.addAccount(newAccount);
        this.account.add(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin){
        for (User u : this.user){
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;

            }
        }
        //haven't found the user or have an incorrect pin
        return null;
    }
    public void addAccount(Account newAccount) {
        this.account.add(newAccount);
    }
    public String getName() {
        return this.name;
    }
}
