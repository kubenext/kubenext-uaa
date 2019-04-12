package com.github.kubenext.uaa.security;

import com.github.kubenext.uaa.domain.Client;
import com.github.kubenext.uaa.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Component;

/**
 * @author shangjin.li
 */
@Component("domainClientDetailsService")
public class DomainClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = clientRepository.findOneByClientId(clientId).orElseThrow(() -> new NoSuchClientException("No client with requested id: " + clientId));
        return new DomainClientDetails(client);
    }


}
