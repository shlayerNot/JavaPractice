import java.util.Date;

public class Transaction {
    
    //the amount for the transaction
    private double amount;

    //The time and date of the transaction
    private Date timestamp;

    //a memo for the transaction
    private String memo;

    //the account in which the transaction was performed
    private Account inAccount;

    public Transaction(double amount, Account inAccount){
        
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";

    }

    public Transaction(double amount, String memo, Account inAccount){
        this(amount, inAccount);

        this.memo = memo;
    }

	public double getAmount() {
		return this.amount;
	}

    //get the string summarizing the transaction 
    //return the summary string
    public String getSummaryLine() {
        if (this.amount >= 0){
            return String.format("%s :  $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else{
            return String.format("%s :  $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        }
    }
}
