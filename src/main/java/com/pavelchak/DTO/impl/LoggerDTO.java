package com.pavelchak.DTO.impl;

import com.pavelchak.DTO.DTO;
import com.pavelchak.domain.Logger;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class LoggerDTO extends DTO<Logger> {
    public LoggerDTO(Logger logger, Link link) {
        super(logger, link);
    }

    public Long getLoggerId() {
        return getEntity().getId();
    }

    public String getPerson() {
        return getEntity().getPerson();
    }

    public String getBook() {
        return getEntity().getBook();
    }

    public String getAction() {
        return getEntity().getAction();
    }

    public String getUser() {
        return getEntity().getUser();
    }

    public String getTime() {
        return getEntity().getTimeStamp().toString();
    }
}
