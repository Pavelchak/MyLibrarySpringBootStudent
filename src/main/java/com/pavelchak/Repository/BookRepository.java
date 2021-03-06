package com.pavelchak.Repository;

import com.pavelchak.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
