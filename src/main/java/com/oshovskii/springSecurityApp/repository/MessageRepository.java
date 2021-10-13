package com.oshovskii.springSecurityApp.repository;

import com.oshovskii.springSecurityApp.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    @Query("SELECT m FROM Message m WHERE m.user.username = ?1")
    List<Message> findAllMessagesByUser_Name(String username);

    Page<Message> findAll(Pageable pageable);
}
