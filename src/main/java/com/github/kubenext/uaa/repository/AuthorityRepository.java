package com.github.kubenext.uaa.repository;

import com.github.kubenext.uaa.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lishangjin
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
