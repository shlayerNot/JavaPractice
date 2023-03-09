import java.util.ArrayList;

public class Account {
    
    //Account name or type
    private String name;

    //Account balance
    private double balance;

    //account id
    private String uuid;

    //The user object that owns the account
    private User holder;

    //the list of transaction this account
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank theBank){

        //set the account name and holder 
        this.name = name;
        this.holder = holder;

        // get new UUID for account
        this.uuid = theBank.getNewAccountUUID();


        //init transaction
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID() {
        return this.uuid;   
    }

    public String getSummaryLine() {
        //get the balance
        double balance = this.getBalance();

        //format the summary line depeding wheter the balance is negative
        if (balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }else{
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }

    }

    public double getBalance(){
        double balance = 0;
        for (Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    //Print trnasaction history of the account
    public void printTransHistory() {
        System.out.printf("\nTransaction History for Account! %s\n", this.uuid);

        for (int t = this.transactions.size()-1; t >= 0; t--){
            System.out.printf("\n%s ", this.transactions.get(t).getSummaryLine());

        }
        System.out.println();
        
    }
    /*
     * Add new transaction in this account
     * @param amount the amount transacted
     * @param memo   the transaction memo
     */
    public void addTransaction(double amount, String memo) {
        //create a new transaction obkject and add it to our l;ist
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
