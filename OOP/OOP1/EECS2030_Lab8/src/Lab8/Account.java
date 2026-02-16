package Lab8;
import java.util.Date;
import java.util.Objects;


/* when you implement Comparable, it is possible that a warning
 * is given, which you should ignore for now, until we discuss Generics. 
 */
public abstract class Account implements Comparable {
	protected int accountNo;
	// add the rest of attributes here

    protected double balance;
    protected String fullName;
    protected Date dateOpened;
    protected double maxTransferable;
	
	/**
	 * This method deposit <code> amount </code> to this account.
	 * @param amount is the amount that is deposited to this account. 
	 */
	// insert your code here

    public abstract void deposit(double amount);
	
	/**
	 * This method withdraw <code> amount </code> from this account.
	 * @param amount is the amount that should be withdrawn from this account
	 * @return It returns true if the transaction is done successfully. 
	 * @throws Exception This methods may throw either <code> NotEnoughMoneyException </code> or
	 * <code> TransferNotAllowedException </code>.
	 */
    public abstract boolean withdraw(double amount) 
    	    throws TransferNotAllowedException, NotEnoughMoneyException;

	/**
	 * This method transfers money from this account to the given account.
	 * @param to is the destination account, where the money is deposited to.
	 * @param amount is the amount of money that is transfered. 
	 * @return It returns true if the transaction is successful. 
	 * @throws Exception This methods may throw either <code> NotEnoughMoneyException </code> or
	 * <code> TransferNotAllowedException </code>.
	 */

	// insert your code here
	
    public boolean transferFrom(Account to, double amount) 
            throws TransferNotAllowedException, NotEnoughMoneyException {
    	
//        from.withdraw(amount);
        this.withdraw(amount); 
//        this.deposit(amount);
        to.deposit(amount);
        return true;
    }
	
	
	
	
	
	/**
	 * This is the accessor method for <code> accountNo </code>
	 * @return It returns the accountNo of this account.
	 */

	// insert your code here

	
    public int getAccountNo() {
        return accountNo;
    }
	
	
	
	/**
	 * This is the accessor method for <code> balance </code>
	 * @return It returns the balance of the account.
	 */

	
	// insert your code here

	
    public double getBalance() {
        return balance;
    }
	
	/**
	 * This is the accessor method for <code> fullName </code>
	 * @return It returns the name of the holder of the account.
	 */

	// insert your code here

	
    public String getFullName() {
        return fullName;
    }
	
	
	/**
	 * This is the accessor method for <code> dateOpened </code>
	 * @return It returns the date at which the account was opened.
	 */

	// insert your code here

    public Date getDateOpened() {
        return dateOpened;
    }
	
	
	
	
	/**
	 * This is the accessor method for <code> maxTransferable </code>
	 * @return It returns the maximum allowed amount that can be withdrawn from this account in one transaction.
	 */

	// insert your code here

	
    public double getMaxTransferable() {
        return maxTransferable;
    }
	
	
	
	/**
	 * This method compares two accounts. If the two accounts have the same values
	 * for all the instance variables, they are considered, equal and this 
	 * method returns 0. If two objects were not equal, the account whose accountNo is less, 
	 * is the smaller object so this method returns -1. Otherwise it returns 1.
	 * @param object is an object of type account. 
	 * @return<pre> It returns 0, if the two objects are equal. 
	 * It returns -1, if this object is less than the object that is passed as a parameter into the method. 
	 * It returns 1, if this object is greater than the object that is passed as a parameter into the method<pre>. 
	 */
	@Override

	// insert your code here
	
    public int compareTo(Object o) {
        if (!(o instanceof Account)) {
            throw new IllegalArgumentException("Not an Account object");
        }
        Account other = (Account) o;
        if (this.accountNo == other.accountNo &&
            Double.compare(this.balance, other.balance) == 0 &&
            this.fullName.equals(other.fullName) &&
            this.dateOpened.equals(other.dateOpened) &&
            Double.compare(this.maxTransferable, other.maxTransferable) == 0) {
            return 0;
        } else {
            return (this.accountNo < other.accountNo) ? -1 : 1;
        }
    }
}


class Current extends Account{
	
	// insert your code here
    public Current(int accountNo, double balance, String fullName, Date dateOpened, double maxTransferable) {
        this.accountNo = accountNo;
        this.balance = balance;
        this.fullName = fullName;
        this.dateOpened = dateOpened;
        this.maxTransferable = maxTransferable;
    }
	
    @Override
    public void deposit(double amount) {
        balance += amount;
    }
    
    @Override
    public boolean withdraw(double amount) 
    	    throws TransferNotAllowedException, NotEnoughMoneyException {
        if (amount > balance) {
            throw new NotEnoughMoneyException("Insufficient balance");
        }
        if (amount > maxTransferable) {
            throw new TransferNotAllowedException("Exceeds max transferable amount");
        }
        balance -= amount;
        return true;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Current current = (Current) obj;
        return accountNo == current.accountNo &&
               Double.compare(current.balance, balance) == 0 &&
               Double.compare(current.maxTransferable, maxTransferable) == 0 &&
               Objects.equals(fullName, current.fullName) &&
               Objects.equals(dateOpened, current.dateOpened);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNo, balance, fullName, dateOpened, maxTransferable);
    }
}

/**
 * This class is a user defined Exception used 
 * when transferring money is not allowed.
 *
 */
	// insert your code here

class TransferNotAllowedException extends Exception {
    public TransferNotAllowedException(String message) { 
        super(message); 
    }
}


/**
 * This is a user defined exception used
 * when a transaction is unsuccessful due to lack
 * of enough money.
 */
	// insert your code here
class NotEnoughMoneyException extends TransferNotAllowedException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}


