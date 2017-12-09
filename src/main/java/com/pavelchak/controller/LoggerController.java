package com.pavelchak.controller;

import com.pavelchak.DTO.DTOBuilder;
import com.pavelchak.DTO.impl.LoggerDTO;
import com.pavelchak.domain.Logger;
import com.pavelchak.exceptions.NoSuchLogException;
import com.pavelchak.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class LoggerController {
    @Autowired
    LoggerService loggerService;

    @GetMapping(value = "/api/logger")
    public ResponseEntity<List<LoggerDTO>> getAllLogger() {
        List<Logger> loggerEntities = loggerService.getAllLogger();
        Link link = linkTo(methodOn(LoggerController.class).getAllLogger()).withSelfRel();
        List<LoggerDTO> loggerDTO = DTOBuilder.buildDtoListForCollection(loggerEntities, LoggerDTO.class, link);
        return new ResponseEntity<>(loggerDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/logger/{log_id}")
    public ResponseEntity<LoggerDTO> getLog(@PathVariable Long log_id) throws NoSuchLogException {
        Logger logger =loggerService.getLog(log_id);
        Link link = linkTo(methodOn(LoggerController.class).getLog(log_id)).withSelfRel();
        LoggerDTO loggerDTO = DTOBuilder.buildDtoForEntity(logger, LoggerDTO.class, link);
        return new ResponseEntity<>(loggerDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/logger/filter/surname/{surname}")
    public ResponseEntity<List<LoggerDTO>> getLoggerFilterBySurname(@PathVariable String surname) {
        List<Logger> loggerEntities = loggerService.getLoggerFilterBySurname(surname);
        Link link = linkTo(methodOn(LoggerController.class).getAllLogger()).withSelfRel();
        List<LoggerDTO> loggerDTO = DTOBuilder.buildDtoListForCollection(loggerEntities, LoggerDTO.class, link);
        return new ResponseEntity<>(loggerDTO, HttpStatus.OK);
    }
}
