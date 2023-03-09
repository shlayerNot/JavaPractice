import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        //init scanner
        Scanner input = new Scanner(System.in);

        //init Bank
        Bank theBank = new Bank("Bank of the Philippines");

        //Add the user, which also creates a savings account
        User aUser = theBank.addUser("Renz", "Basilan", "1234");


        //Add a checking account  for our user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){
            //stay in login prompt until sucessful login  
            curUser = App.mainMenuPrompt(theBank, input);

            //stay in main menu until  user quits 
            App.printUserMenu(curUser, input);
        }

    }

    public static User mainMenuPrompt(Bank theBank, Scanner input){

        String userID;
        String pin;
        User authUser;


        //prompt the user for user id/ pin combo unitl a correct one is reached
        do{

            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = input.nextLine();
            System.out.print("Enter Pin: ");
            pin = input.nextLine();

            //try to get the user object corresponding to the id and pin combo 
            authUser =  theBank.userLogin(userID, pin);
            if (authUser == null){
                System.out.println("Incorrect User ID/pin combination " + "Please try again.");
            }
        }while(authUser == null);//conitnue looping until sucessfull login 

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner input){

        //print summary of the user's account 
        theUser.printAccountsSummary();

        //init
        int choice;

        do{
            System.out.printf("Welcome  %s, what would you like to do?", theUser.getFirstName());
            System.out.println(" \n 1) Show the Transaction History ");
            System.out.println(" 2) Withdraw ");
            System.out.println(" 3) Deposit ");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.print("Enter Choice: ");
            choice = input.nextInt();
            input.nextLine();

            if (choice <1 || choice > 5){
                System.out.println("Invalid Choice. PLease choose 1-5");
            }
        }while(choice <1 || choice > 5);

        switch (choice){
            case 1: 
                App.showTransactionHistory(theUser, input);
                break;
            case 2:
                App.withdrawalFunds(theUser, input);
                break;
            case 3: 
                App.depositFunds(theUser, input);
                break;
            case 4:
                App.transferFunds(theUser, input);
                break;
        }
        if (choice != 5){
            App.printUserMenu(theUser, input);
        }
    }

    private static void showTransactionHistory(User theUser, Scanner input) {
        int theAcct;

        //get account whose transaction history is looking
        do{
            System.out.printf("Enter the number (1-%d) of the Account" + " whose transaction you want to see: ", theUser.numAccounts());

            theAcct = input.nextInt()-1;

            if (theAcct < 0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid Account. Please try again.");
            }
        } while(theAcct < 0 || theAcct >= theUser.numAccounts());

        //print transaction history
        theUser.printAcctTansHistory(theAcct);
    }

    /*
     * Deposit an amount to the account
     * @param theUSer the logged-in User object
     * @param input     the Scanner object for user input
     */
    private static void depositFunds(User theUser, Scanner input) {

           //init
           int fromAcct;
           double amount;
           double actBal;
           String memo;

           do{

               System.out.printf("Enter the number (1-%d) of the account\n" + "to deposit : ", theUser.numAccounts());

               fromAcct = input.nextInt()-1;

               if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                   System.out.println("Invalid Account. Please try again.");
               }
           } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
           
           actBal = theUser.getAcctBal(fromAcct);

       //get the amoun to transfer
       do{
           System.out.printf("Enter the number amount to deposit: $", actBal);
           
           amount = input.nextDouble();

           if (amount <= 0){
               System.out.println("Amount must be graeter than zero. ");
           }

       } while(amount <= 0);

       input.nextLine();

       //get a memo
       System.out.print("Enter a memo: ");
       memo = input.nextLine();

       //do the withdraw
       theUser.addAcctTransaction(fromAcct, amount, memo);

    
    }

    /*
     * Process a fund withdrawal from an account
     * @param theUser   the logged-in User object
     * @param input     the Scanner object user for user input
     */
    private static void withdrawalFunds(User theUser, Scanner input) {

            //init
            int fromAcct;
            double amount;
            double actBal;
            String memo;

            do{

                System.out.printf("Enter the number (1-%d) of the account\n" + "to withdraw from ", theUser.numAccounts());

                fromAcct = input.nextInt()-1;

                if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                    System.out.println("Invalid Account. Please try again.");
                }
            } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
            
            actBal = theUser.getAcctBal(fromAcct);

        //get the amoun to transfer
        do{
            System.out.printf("Enter the number amount to withdraw (max $%.02f): $", actBal);
            
            amount = input.nextDouble();

            if (amount <= 0){
                System.out.println("Amount must be greater than zero. ");
            } else if (amount > actBal){
                System.out.printf("Amount must not be greater than;\nbalance of %.02f.\n", actBal);
            } 

        } while(amount <= 0 || amount > actBal);

        input.nextLine();

        //get a memo
        System.out.print("Enter a memo: ");
        memo = input.nextLine();

        //do the withdraw
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }

    private static void transferFunds(User theUser, Scanner input) {

        //init
        int fromAcct;
        int toAcct;
        double amount;
        double actBal;

        //get the Account to transfer from
        do{

            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from : ", theUser.numAccounts());

            fromAcct = input.nextInt()-1;

            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid Account. Please try again.");
            }
        } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        
        actBal = theUser.getAcctBal(fromAcct);

        //get the Acct to transfer to 
        do{

            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from ", theUser.numAccounts());

            toAcct = input.nextInt()-1;

            if (toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid Account. Please try again.");
            }
        } while(toAcct < 0 || toAcct >= theUser.numAccounts());

        //get the amoun to transfer
        do{
            System.out.printf("Enter the number amount to transfer (max $%.02f): $", actBal);
            
            amount = input.nextDouble();

            if (amount <= 0){
                System.out.println("Amount must be greater than zero. ");
            } else if (amount > actBal){
                System.out.printf("Amount must not be greater than;\nbalance of %.02f.\n", actBal);
            }

        } while(amount <= 0 || amount > actBal);


        //finally do the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to Account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(fromAcct, amount, String.format("Transfer from Account %s", theUser.getAcctUUID(fromAcct)));
    }
}
