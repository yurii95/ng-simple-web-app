package com.globallogic.mykolaiv.campustrainees.simplewebapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "books")
public class BookEntity {
    @Id
    private String idBook;

    @NotNull
    private String title;

    @NotNull
    private String genre;

    @NotNull
    private String authors;

    @NotNull
    private String pages;

    @NotNull
    private String description;

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", authors='" + authors + '\'' +
                ", pages='" + pages + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
