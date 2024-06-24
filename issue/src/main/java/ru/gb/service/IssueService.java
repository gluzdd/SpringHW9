package ru.gb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.model.Issue;
import ru.gb.provider.BookProvider;
import ru.gb.provider.ReaderProvider;
import ru.gb.repository.IssueRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IssueService {

    @Value("${application.max-allowed-books:1}")
    private int limitBooks;

    // спринг это все заинжектит
    private final BookProvider bookRepository;
    private final ReaderProvider readerRepository;
    private final IssueRepository issueRepository;

    @Autowired
    public IssueService(BookProvider bookRepository, ReaderProvider readerRepository, IssueRepository issueRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.issueRepository = issueRepository;
    }

    public Issue issue(Issue issue) {
        if (bookRepository.getBookById(issue.getBookId()).isEmpty()) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + issue.getBookId() + "\"");
        }
        if (readerRepository.getReaderById(issue.getReaderId()).isEmpty()) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + issue.getReaderId() + "\"");
        }
        // можно проверить, что у читателя нет книг на руках (или его лимит не превышает в Х книг)
        if (issueRepository.getIssueByReaderId(issue.getReaderId()).size() >= limitBooks) {
            throw new NullPointerException("Макс кол-во книг \"" + issue.getReaderId() + "\"");
        }
        //Issue issue2 = new Issue(issue.getBookId(), issue.getReaderId());
        issue.setIssuedAt(LocalDateTime.now());
        issueRepository.save(issue);
        return issue;
    }


    public List<Issue> getIssue() {
        return issueRepository.findAll();
    }

    public Issue getIssueById(Long id) {
        return issueRepository.findById(id).get();
    }

    @Transactional
    public Issue returnBooks(Long id) {
        Issue updateIssue = issueRepository.findById(id).orElseThrow(()-> new RuntimeException("Issue not found"));
        updateIssue.setTimeReturn(LocalDateTime.now());
        return updateIssue;
    }

    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }

//    public void returnBooks(Long id) {
//        Issue issue;
//        if (issueRepository.getIssueById(id) != null) {
//            issueRepository.getIssueById(id).setTimeReturn(LocalDateTime.now());
//        }
//    }


}
