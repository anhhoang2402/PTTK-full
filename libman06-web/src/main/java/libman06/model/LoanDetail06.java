package libman06.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanDetail06 implements Serializable {
    public static final float FINE_PER_DAY = 2000;
    public static final String STATUS_BORROWING = "BORROWING";
    public static final String STATUS_RETURNED = "RETURNED";

    private int id;
    private LoanSlip06 loanSlip;
    private Document06 document;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private float lateFine;
    private float damageFine;
    private String status;
    private String note;

    public LoanDetail06() {
    }

    public float calculateLateFine() {
        lateFine = getDaysLate() * FINE_PER_DAY;
        return lateFine;
    }

    public long getDaysLate() {
        LocalDate until = (returnDate != null) ? returnDate : LocalDate.now();
        return Math.max(0, ChronoUnit.DAYS.between(dueDate, until));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LoanSlip06 getLoanSlip() { return loanSlip; }
    public void setLoanSlip(LoanSlip06 loanSlip) { this.loanSlip = loanSlip; }

    public Document06 getDocument() { return document; }
    public void setDocument(Document06 document) { this.document = document; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public float getLateFine() { return lateFine; }
    public void setLateFine(float lateFine) { this.lateFine = lateFine; }

    public float getDamageFine() { return damageFine; }
    public void setDamageFine(float damageFine) { this.damageFine = damageFine; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
