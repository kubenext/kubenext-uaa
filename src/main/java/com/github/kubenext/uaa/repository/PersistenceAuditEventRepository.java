package com.github.kubenext.uaa.repository;

import com.github.kubenext.uaa.domain.PersistentAuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
@Repository
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByEventDateAfter(Instant after);

    List<PersistentAuditEvent> findByPrincipalAndEventDateAfter(String principal, Instant after);

    List<PersistentAuditEvent> findByPrincipalAndEventDateAfterAndEventType(String principal, Instant after, String type);

    Page<PersistentAuditEvent> findAllByEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable);
}
