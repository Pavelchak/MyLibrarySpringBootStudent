package com.pavelchak.DTO.impl;

import com.pavelchak.DTO.DTO;
import com.pavelchak.controller.PersonController;
import com.pavelchak.domain.City;
import com.pavelchak.exceptions.NoSuchCityException;
import com.pavelchak.exceptions.NoSuchPersonException;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


public class CityDTO extends DTO<City> {
    public CityDTO(City city, Link link) throws NoSuchCityException, NoSuchPersonException {
        super(city, link);
        add(linkTo(methodOn(PersonController.class).getPersonsByCityID(getEntity().getId())).withRel("persons"));
    }

    public Long getCityId() { return getEntity().getId(); }

    public String getCity() {
        return getEntity().getCity();
    }
}
