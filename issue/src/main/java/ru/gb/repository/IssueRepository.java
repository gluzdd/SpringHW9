package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.model.Issue;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    //@Query("SELECT i FROM Issue i WHERE i.readerId = :id")
    List<Issue> getIssueByReaderId(Long id);

}
