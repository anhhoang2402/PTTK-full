package libman06.model;

import java.io.Serializable;
import java.time.LocalDate;

public class ReaderCard06 implements Serializable {
    public static final String STATUS_ACTIVE = "ACTIVE";

    private int id;
    private String cardCode;
    private String cardType;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;
    private Reader06 reader;

    public ReaderCard06() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCardCode() { return cardCode; }
    public void setCardCode(String cardCode) { this.cardCode = cardCode; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Reader06 getReader() { return reader; }
    public void setReader(Reader06 reader) { this.reader = reader; }
}
