package com.pavelchak.DTO.impl;

import com.pavelchak.DTO.DTO;
import com.pavelchak.controller.PersonController;
import com.pavelchak.domain.Book;
import com.pavelchak.exceptions.NoSuchBookException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class BookDTO extends DTO<Book> {
    public BookDTO(Book book, Link link) throws NoSuchBookException {
        super(book, link);
        add(linkTo(methodOn(PersonController.class).getPersonsByBookID(getEntity().getId())).withRel("persons"));
    }

    public Long getBookId() {
        return getEntity().getId();
    }

    public String getBookName() {
        return getEntity().getBookName();
    }

    public String getAuthor() {
        return getEntity().getAuthor();
    }

    public String getPublisher() {
        return getEntity().getPublisher();
    }

    public Integer getImprintYear() {
        return getEntity().getImprintYear();
    }

    public Integer getAmount() {
        return getEntity().getAmount();
    }

}
