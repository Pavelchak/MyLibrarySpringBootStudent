package com.pavelchak.service;

import com.pavelchak.Repository.*;
import com.pavelchak.domain.*;
import com.pavelchak.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    BookRepository bookRepository;

    public List<Person> getPersonByCityId(Long city_id) throws NoSuchCityException {
        City city =cityRepository.findOne(city_id);
        if(city ==null)throw new NoSuchCityException();
        return city.getPersons();
    }

    public Person getPerson(Long person_id) throws NoSuchPersonException {
        Person person = personRepository.findOne(person_id);
        if(person ==null) throw new NoSuchPersonException();
        return person;
    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    public Set<Person> getPersonsByBookId(Long book_id) throws NoSuchBookException {
        Book book = bookRepository.findOne(book_id);
        if(book ==null) throw new NoSuchBookException();
        return book.getPersons();
    }

    @Transactional
    public void createPerson(Person person, Long city_id) throws NoSuchCityException {
        if(city_id>0) {
            City city = cityRepository.findOne(city_id);
            if (city == null) throw new NoSuchCityException();
            person.setCity(city);
        }
        personRepository.save(person);
    }

    @Transactional
    public void updatePerson(Person uPerson, Long person_id, Long city_id) throws NoSuchCityException, NoSuchPersonException {
        City city = cityRepository.findOne(city_id);
        if(city_id>0) {
            if (city == null) throw new NoSuchCityException();
        }
        Person person = personRepository.findOne(person_id);
        if(person ==null) throw new NoSuchPersonException();
        //update
        person.setSurname(uPerson.getSurname());
        person.setName(uPerson.getName());
        person.setEmail(uPerson.getEmail());
        if(city_id>0) person.setCity(city);
        else          person.setCity(null);
        person.setStreet(uPerson.getStreet());
        person.setApartment(uPerson.getApartment());
        personRepository.save(person);
    }

    @Transactional
    public void deletePerson(Long person_id) throws NoSuchPersonException, ExistsBooksForPersonException {
        Person person = personRepository.findOne(person_id);
        if(person ==null) throw new NoSuchPersonException();
        if(person.getBooks().size()!=0)  throw new ExistsBooksForPersonException();
        personRepository.delete(person);
    }

    @Transactional
    public void addBookForPerson(Long person_id, Long book_id)
            throws NoSuchPersonException, NoSuchBookException, AlreadyExistsBookInPersonException, BookAbsentException {
        Person person = personRepository.findOne(person_id);
        if(person ==null) throw new NoSuchPersonException();
        Book book = bookRepository.findOne(book_id);
        if(book ==null) throw new NoSuchBookException();
        if(person.getBooks().contains(book)==true ) throw new AlreadyExistsBookInPersonException();
        if( book.getAmount()<= book.getPersons().size() ) throw new BookAbsentException();
        person.getBooks().add(book);
        personRepository.save(person);
    }

    @Transactional
    public void removeBookForPerson(Long person_id, Long book_id)
            throws NoSuchPersonException, NoSuchBookException, PersonHasNotBookException {
        Person person = personRepository.findOne(person_id);
        if(person ==null) throw new NoSuchPersonException();
        Book book = bookRepository.findOne(book_id);
        if(book ==null) throw new NoSuchBookException();
        if(person.getBooks().contains(book)==false ) throw new PersonHasNotBookException();
        person.getBooks().remove(book);
        personRepository.save(person);
    }




}
