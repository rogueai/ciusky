package dev.rogueai.collection.service.model;

import dev.rogueai.collection.controller.validation.IsbnConstraint;
import dev.rogueai.collection.controller.validation.IsbnFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Book extends Ciusky {

    @Size(max = 256)
    private String author;

    @Size(max = 256)
    private String format;

    @Size(max = 256)
    private String publisher;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    @IsbnConstraint
    @IsbnFormat
    @NotBlank
    private String isbn13;

    @Size(max = 2)
    private String language;

    public Book() {

    }

    public Book(Ciusky ciusky) {
        this.id = ciusky.id;
        this.title = ciusky.title;
        this.description = ciusky.description;
        this.type = ciusky.type;
        this.quality = ciusky.quality;
        this.purchasePlace = ciusky.purchasePlace;
        this.purchaseDate = ciusky.purchaseDate;
        this.paidPrice = ciusky.paidPrice;
        this.marketPrice = ciusky.marketPrice;
        this.notes = ciusky.notes;
        this.tags = ciusky.tags;
        this.images = ciusky.images;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
