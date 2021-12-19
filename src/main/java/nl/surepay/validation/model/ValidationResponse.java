package nl.surepay.validation.model;

import java.io.Serializable;

public class ValidationResponse implements Serializable {

    private static final long serialVersionUID = 1234L;

    Integer transactionReference;
    String description;

    public ValidationResponse() {
    }

    public ValidationResponse(Integer transactionReference, String description) {
        this.transactionReference = transactionReference;
        this.description = description;
    }

    public Integer getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(Integer transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return this.transactionReference.equals(((ValidationResponse) obj).transactionReference);
    }
}
