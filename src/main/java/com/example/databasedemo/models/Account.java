package com.example.databasedemo.models;


import java.util.Objects;

public class Account {
    private long accountId;
    private long accountVersion;
    private String accountName;
    private String accountNumber;
    private String currency;
    private String assetLocation;
    private String note;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountVersion() {
        return accountVersion;
    }

    public void setAccountVersion(long accountVersion) {
        this.accountVersion = accountVersion;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAssetLocation() {
        return assetLocation;
    }

    public void setAssetLocation(String assetLocation) {
        this.assetLocation = assetLocation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                accountVersion == account.accountVersion &&
                accountName.equals(account.accountName) &&
                accountNumber.equals(account.accountNumber) &&
                currency.equals(account.currency) &&
                assetLocation.equals(account.assetLocation) &&
                Objects.equals(note, account.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountVersion, accountName, accountNumber, currency, assetLocation, note);
    }
}
