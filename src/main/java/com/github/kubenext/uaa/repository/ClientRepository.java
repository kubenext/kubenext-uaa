package com.github.kubenext.uaa.repository;

import com.github.kubenext.uaa.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> findOneByClientId(String clientId);

}
