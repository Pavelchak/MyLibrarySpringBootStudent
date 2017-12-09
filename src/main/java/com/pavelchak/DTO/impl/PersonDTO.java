package com.pavelchak.DTO.impl;

import com.pavelchak.DTO.DTO;
import com.pavelchak.controller.BookController;
import com.pavelchak.domain.Person;
import com.pavelchak.exceptions.NoSuchPersonException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PersonDTO extends DTO<Person> {
    public PersonDTO(Person person, Link link) throws NoSuchPersonException {
        super(person, link);
        add(linkTo(methodOn(BookController.class).getBooksByPersonID(getEntity().getId())).withRel("books"));
    }

    public Long getPersonId() {
        return getEntity().getId();
    }

    public String getSurname() {
        return getEntity().getSurname();
    }

    public String getName() {
        return getEntity().getName();
    }

    public String getEmail() {
        return getEntity().getEmail();
    }

    public String getCity() {
        if(getEntity().getCity()==null) return "";
        return getEntity().getCity().getCity();
    }

    public String getStreet() {
        return getEntity().getStreet();
    }

    public String getApartment() {
        return getEntity().getApartment();
    }


}
