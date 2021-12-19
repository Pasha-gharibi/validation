package nl.surepay.validation.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ValidationRequest implements Serializable {

        /*
       File Format :
       column 0   Transaction reference      A numeric value
       column 1   Account number             An IBAN
       column 2   Description                Free text
       column 3   Start Balance              The starting balance in Euros
       column 4   Mutation                   Either and addition (+) or a deduction (-)
       column 5   End Balance                The end balance in Euros */

    private static final long serialVersionUID = 1235L;

    Integer reference;
    String accountNumber;
    String description;
    BigDecimal startBalance;
    BigDecimal mutation;
    BigDecimal endBalance;

    public ValidationRequest() {
    }

    public ValidationRequest(Integer reference, String accountNumber, String description, BigDecimal startBalance,
                             BigDecimal mutation, BigDecimal endBalance) {
        this.reference = reference;
        this.accountNumber = accountNumber;
        this.description = description;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.endBalance = endBalance;
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(BigDecimal startBalance) {
        this.startBalance = startBalance;
    }

    public BigDecimal getMutation() {
        return mutation;
    }

    public void setMutation(BigDecimal mutation) {
        this.mutation = mutation;
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "reference=" + reference +
                ", accountNumber='" + accountNumber + '\'' +
                ", description='" + description + '\'' +
                ", startBalance=" + startBalance +
                ", mutation=" + mutation +
                ", endBalance=" + endBalance +
                '}';
    }
}
