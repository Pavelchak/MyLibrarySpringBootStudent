package com.pavelchak.controller;

import com.pavelchak.DTO.DTOBuilder;
import com.pavelchak.DTO.impl.BookDTO;

import com.pavelchak.domain.Book;
import com.pavelchak.exceptions.*;
import com.pavelchak.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping(value = "/api/book/person/{person_id}")
    public ResponseEntity<List<BookDTO>> getBooksByPersonID(@PathVariable Long person_id) throws NoSuchPersonException {
        Set<Book> bookList = bookService.getBooksByPersonId(person_id);
        Link link = linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel();
        List<BookDTO> personDTO = DTOBuilder.buildDtoListForCollection(bookList, BookDTO.class, link);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/book/{book_id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long book_id) throws NoSuchBookException {
        Book book = bookService.getBook(book_id);
        Link link = linkTo(methodOn(BookController.class).getBook(book_id)).withSelfRel();
        BookDTO bookDTO = DTOBuilder.buildDtoForEntity(book, BookDTO.class, link);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/book")
    public ResponseEntity<List<BookDTO>> getAllBooks()  {
        List<Book> bookList = bookService.getAllBooks();
        Link link = linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel();
        List<BookDTO> personDTO = DTOBuilder.buildDtoListForCollection(bookList, BookDTO.class, link);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/book")
    public  ResponseEntity<BookDTO> addBook(@RequestBody Book newBook) throws NoSuchBookException {
        bookService.createBook(newBook);
        Link link = linkTo(methodOn(BookController.class).getBook(newBook.getId())).withSelfRel();
        BookDTO bookDTO = DTOBuilder.buildDtoForEntity(newBook,BookDTO.class, link);
        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/book/{book_id}")
    public  ResponseEntity<BookDTO> updateBook(@RequestBody Book uBook, @PathVariable Long book_id) throws NoSuchBookException {
        bookService.updateBook(uBook,book_id);
        Book book =bookService.getBook(book_id);
        Link link = linkTo(methodOn(BookController.class).getBook(book_id)).withSelfRel();
        BookDTO bookDTO = DTOBuilder.buildDtoForEntity(book,BookDTO.class, link);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/book/{book_id}")
    public  ResponseEntity deleteBook(@PathVariable Long book_id) throws ExistsPersonForBookException, NoSuchBookException {
        bookService.deleteBook(book_id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
