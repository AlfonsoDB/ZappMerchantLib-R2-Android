package com.zapp.core;

/**
 * The financial account between a bank customer and a financial institution.
 */
public class BankAccount implements IValidatable {

    /**
     * CFI Account Number.
     */
    private String number;

    /**
     * Account name.
     */
    private String name;

    /**
     * CFI Sort Code.
     */
    private String sortCode;

    /**
     * Balance.
     */
    private CurrencyAmount balance;

    /**
     * The available funds.
     */
    private CurrencyAmount availableFunds;

    /**
     * The default constructor.
     */
    public BankAccount(final String number, final String name, final String sortCode, final CurrencyAmount balance, final CurrencyAmount availableFunds)
            throws ZappModelValidationException {
        this.number = number;
        this.name = name;
        this.sortCode = sortCode;
        this.balance = balance;
        this.availableFunds = availableFunds;
        validate();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(final String sortCode) {
        this.sortCode = sortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public CurrencyAmount getBalance() {
        return balance;
    }

    public void setBalance(final CurrencyAmount balance) {
        this.balance = balance;
    }

    public CurrencyAmount getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(final CurrencyAmount availableFunds) {
        this.availableFunds = availableFunds;
    }

    @Override
    public final void validate() throws ZappModelValidationException {
        ValidationUtils.require(number, "BankAccount.number");
        ValidationUtils.require(name, "BankAccount.name");
        ValidationUtils.require(sortCode, "BankAccount.sortCode");
        ValidationUtils.require(balance, "BankAccount.balance");
        balance.validate();
        ValidationUtils.require(availableFunds, "BankAccount.availableFunds");
        availableFunds.validate();
    }

    @Override
    public String toString() {
        return String.format("BankAccount[%s, %s, %s, %s, %s]", name, number, sortCode, balance, availableFunds);
    }

    @Override
    public int hashCode() {
        return (number + "/" + sortCode).hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        try {
            validate();
            if (obj instanceof BankAccount) {
                final BankAccount bankAccount = (BankAccount) obj;
                bankAccount.validate();
                if (number.equals(bankAccount.number) && sortCode.equals(bankAccount.sortCode)) {
                    return true;
                }
            }
        } catch (ZappModelValidationException zmve) {
            //nothing to do here as final return will indicate that given object does not equal
        }
        return false;
    }
}
