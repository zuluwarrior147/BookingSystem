package entity;

import java.math.BigDecimal;

/**
 * Created by Zulu Warrior on 5/21/2017.
 */
public class Account {
    public final static long DEFAULT_NUMBER = 0L;
    public final static String DEFAULT_BALANCE = "0.00";

    public enum Status {
        ACTIVE, PENDING, BLOCKED
    }

    private long accountNumber;
    private User accountHolder;
    private BigDecimal balance;
    private Status status;


    public Account() {
    }

    public Account(long accountNumber, User accountHolder,
                   BigDecimal balance, Status status) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.status = status;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public User getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(User user) {
        this.accountHolder = accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public boolean isPending() {
        return status == Status.PENDING;
    }

    public boolean isBlocked() {
        return status == Status.BLOCKED;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "holder=" + accountHolder +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return accountNumber == account.accountNumber;
    }

    @Override
    public int hashCode() {
        return (int) (accountNumber ^ (accountNumber >>> 32));
    }
}
