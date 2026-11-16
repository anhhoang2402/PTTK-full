package libman06.model;

import java.io.Serializable;
import java.time.LocalDate;

public class LoanSlip06 implements Serializable {
    private int id;
    private LocalDate borrowDate;
    private String note;
    private ReaderCard06 readerCard;
    private Staff06 staff;

    public LoanSlip06() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public ReaderCard06 getReaderCard() { return readerCard; }
    public void setReaderCard(ReaderCard06 readerCard) { this.readerCard = readerCard; }

    public Staff06 getStaff() { return staff; }
    public void setStaff(Staff06 staff) { this.staff = staff; }
}
