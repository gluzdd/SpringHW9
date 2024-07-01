package ru.gb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.aspects.Timer;
import ru.gb.model.Book;
import ru.gb.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Timer
public class BookController {

    private final BookService service;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> someBook = service.getBookById(id);
        if(someBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(service.getBookById(id).orElseThrow(null));
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(service.getBooks());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(service.addBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook (@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(service.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBook(@PathVariable Long id) {
        service.removeBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
