package libman06.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReturnInvoice06 implements Serializable {
    private int id;
    private LocalDate returnDate;
    private float totalFine;
    private String note;
    private Staff06 staff;
    private ReaderCard06 readerCard;
    private ArrayList<LoanDetail06> listLoanDetail;

    public ReturnInvoice06() {
        listLoanDetail = new ArrayList<>();
    }

    public ReturnInvoice06(Staff06 staff, ReaderCard06 card, ArrayList<LoanDetail06> list) {
        this.staff = staff;
        this.readerCard = card;
        this.listLoanDetail = list;
        this.returnDate = LocalDate.now();
    }

    public float calculateTotalFine() {
        float total = 0;
        for (LoanDetail06 detail : listLoanDetail) {
            total += detail.getLateFine() + detail.getDamageFine();
        }
        totalFine = total;
        return totalFine;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public float getTotalFine() { return totalFine; }
    public void setTotalFine(float totalFine) { this.totalFine = totalFine; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Staff06 getStaff() { return staff; }
    public void setStaff(Staff06 staff) { this.staff = staff; }

    public ReaderCard06 getReaderCard() { return readerCard; }
    public void setReaderCard(ReaderCard06 readerCard) { this.readerCard = readerCard; }

    public ArrayList<LoanDetail06> getListLoanDetail() { return listLoanDetail; }
    public void setListLoanDetail(ArrayList<LoanDetail06> listLoanDetail) { this.listLoanDetail = listLoanDetail; }
}
