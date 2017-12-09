package com.pavelchak.controller;

import com.pavelchak.DTO.DTOBuilder;
import com.pavelchak.DTO.impl.PersonDTO;
import com.pavelchak.domain.Person;
import com.pavelchak.exceptions.*;
import com.pavelchak.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping(value = "/api/person/city/{city_id}")
    public ResponseEntity<List<PersonDTO>> getPersonsByCityID(@PathVariable Long city_id) throws NoSuchCityException, NoSuchPersonException {
        List<Person> personList = personService.getPersonByCityId(city_id);

        Link link = linkTo(methodOn(PersonController.class).getAllPersons()).withSelfRel();

        List<PersonDTO> personDTO = DTOBuilder.buildDtoListForCollection(personList, PersonDTO.class, link);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/person/{person_id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable Long person_id) throws NoSuchPersonException {
        Person person = personService.getPerson(person_id);
        Link link = linkTo(methodOn(PersonController.class).getPerson(person_id)).withSelfRel();
        PersonDTO personDTO = DTOBuilder.buildDtoForEntity(person, PersonDTO.class, link);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/person")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<Person> personList=personService.getAllPersons();
        Link link = linkTo(methodOn(PersonController.class).getAllPersons()).withSelfRel();
        List<PersonDTO> cities = DTOBuilder.buildDtoListForCollection(personList, PersonDTO.class, link);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping(value = "/api/person/book/{book_id}")
    public ResponseEntity<List<PersonDTO>> getPersonsByBookID(@PathVariable Long book_id) throws NoSuchBookException {
        Set<Person> personList = personService.getPersonsByBookId(book_id);
        Link link = linkTo(methodOn(PersonController.class).getAllPersons()).withSelfRel();
        List<PersonDTO> personDTO = DTOBuilder.buildDtoListForCollection(personList, PersonDTO.class, link);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/person/city/{city_id}")
    public  ResponseEntity<PersonDTO> addPerson(@RequestBody Person newPerson, @PathVariable Long city_id)
            throws NoSuchCityException, NoSuchPersonException {
        personService.createPerson(newPerson, city_id);
        Link link = linkTo(methodOn(PersonController.class).getPerson(newPerson.getId())).withSelfRel();
        PersonDTO personDTO = DTOBuilder.buildDtoForEntity(newPerson,PersonDTO.class, link);
        return new ResponseEntity<>(personDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/person/{person_id}/city/{city_id}")
    public  ResponseEntity<PersonDTO> updatePerson(@RequestBody Person uPerson,
                                                   @PathVariable Long person_id, @PathVariable Long city_id)
            throws NoSuchCityException, NoSuchPersonException {
        personService.updatePerson(uPerson, person_id, city_id);
        Person person =personService.getPerson(person_id);
        Link link = linkTo(methodOn(PersonController.class).getPerson(person_id)).withSelfRel();
        PersonDTO personDTO = DTOBuilder.buildDtoForEntity(person,PersonDTO.class, link);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/person/{person_id}")
    public  ResponseEntity deletePerson(@PathVariable Long person_id) throws NoSuchPersonException, ExistsBooksForPersonException {
        personService.deletePerson(person_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/api/person/{person_id}/book/{book_id}")
    public  ResponseEntity<PersonDTO> addBookForPerson(@PathVariable Long person_id, @PathVariable Long book_id)
            throws NoSuchPersonException, NoSuchBookException, AlreadyExistsBookInPersonException, BookAbsentException {
        personService.addBookForPerson(person_id,book_id);
        Person person = personService.getPerson(person_id);
        Link link = linkTo(methodOn(PersonController.class).getPerson(person_id)).withSelfRel();
        PersonDTO personDTO = DTOBuilder.buildDtoForEntity(person,PersonDTO.class, link);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/person/{person_id}/book/{book_id}")
    public  ResponseEntity<PersonDTO> removeBookForPerson(@PathVariable Long person_id, @PathVariable Long book_id)
            throws NoSuchPersonException, NoSuchBookException, PersonHasNotBookException {
        personService.removeBookForPerson(person_id,book_id);
        Person person = personService.getPerson(person_id);
        Link link = linkTo(methodOn(PersonController.class).getPerson(person_id)).withSelfRel();
        PersonDTO personDTO = DTOBuilder.buildDtoForEntity(person,PersonDTO.class, link);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

}
