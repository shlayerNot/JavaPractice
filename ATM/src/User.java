import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    //First name of user
    private String firstName;

    //Last name of user
    private String lastName;

    //The id number of the user
    private String uuid;

    //pin of the user
    private byte pinHash[];

    //list of acc for the users
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank){
        this.firstName = firstName;
        this.lastName = lastName;
        
        //MD5 hashing algo
        try{
        MessageDigest md = MessageDigest.getInstance("MD5");
        this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e){
            System.out.println("Error!!!!!!!!!!");
            e.printStackTrace();
            System.exit(1);
        }

        //get a new, Unique univeral ID for user
        this.uuid = theBank.getNewUserUUID();

        //create empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print log message
        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
    }

    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    //getter for uuid
    public String getUUID(){
        return this.uuid;
    }

    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error!!!!!!!!!!");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);

        for (int a = 0; a < this.accounts.size(); a++){
            System.out.printf("%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTansHistory(int acctIndex) {
        this.accounts.get(acctIndex).printTransHistory();
    }

    //get the balance of a particular account 
    //param acctIdx the index of the account use
    //return        the balance of the account
    public double getAcctBal(int acctIndex) {
       return this.accounts.get(acctIndex).getBalance();
    }


    //get the uuid of a particular account
    //@param acctidx the index of the account use
    //@return the UUID of the Account  
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    /* Add transaction to a particular account
     * @param   acctIdx the index of the account
     * @param   amout   the amount of the transaction
     * @param   memo    the memo of the transaction
     */
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }

    


}
