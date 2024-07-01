package ru.gb.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.aspects.Timer;
import ru.gb.model.Reader;
import ru.gb.provider.IssueProvider;
import ru.gb.service.ReaderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/readers")
@Timer
public class ReaderController {
    private final ReaderService service;

    @Autowired
    public ReaderController(ReaderService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getReaderById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<Reader>> getReaders() {
        return ResponseEntity.ok(service.getReaders());
    }

    @PostMapping
    public ResponseEntity<Reader> createReader(@RequestBody Reader reader) {
        return new ResponseEntity<>(service.addReader(reader), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reader> updateReader(@PathVariable Long id, @RequestBody Reader reader) {
        return ResponseEntity.ok(service.updateReader(id, reader));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReader(@PathVariable Long id) {
        service.removeReader(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/issue")
    public ResponseEntity<Optional<IssueProvider>> getAllIssueByReaderId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getListIssueById(id));
    }
}
