package com.github.kubenext.uaa.repository;

import com.github.kubenext.uaa.domain.Client;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    String CLIENTS_BY_CLIENT_ID_CACHE = "clientsByClientId";

    @Cacheable(cacheNames = CLIENTS_BY_CLIENT_ID_CACHE)
    Optional<Client> findOneByClientId(String clientId);

}
