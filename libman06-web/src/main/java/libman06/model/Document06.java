package libman06.model;

import java.io.Serializable;

public class Document06 implements Serializable {
    private int id;
    private String code;
    private String title;
    private String author;
    private String publisher;
    private int publishYear;
    private String category;
    private int quantity;
    private float price;

    public Document06() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}
