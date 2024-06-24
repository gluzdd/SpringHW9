package ru.gb.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.model.Reader;
import ru.gb.provider.IssueProvider;
import ru.gb.repository.ReaderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {

    private final ReaderRepository repository;
    private final IssueProvider issueRepository;

    public ReaderService(ReaderRepository repository, IssueProvider issueRepository) {
        this.repository = repository;
        this.issueRepository = issueRepository;
    }

    public Reader getReaderById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<Reader> getReaders() {
        return repository.findAll();
    }

    public Reader addReader(Reader reader) {
        return repository.save(reader);
    }

    @Transactional
    public Reader updateReader(Long id, Reader reader) {
        Reader updateReader = repository.findById(id).orElseThrow(()-> new RuntimeException("Reader not found"));
        updateReader.setName(reader.getName());
        return updateReader;
    }

    public void removeReader(Long id) {
        repository.deleteById(id);
    }

    public Optional<IssueProvider> getListIssueById(Long id) {
        return issueRepository.getIssueById(id);
    }
}
