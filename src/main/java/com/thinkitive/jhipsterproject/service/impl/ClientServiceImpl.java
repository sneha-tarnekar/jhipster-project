package com.thinkitive.jhipsterproject.service.impl;

import com.thinkitive.jhipsterproject.domain.Client;
import com.thinkitive.jhipsterproject.repository.ClientRepository;
import com.thinkitive.jhipsterproject.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Mono<Client> save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> update(Client client) {
        log.debug("Request to update Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getClientName() != null) {
                    existingClient.setClientName(client.getClientName());
                }
                if (client.getClientCountry() != null) {
                    existingClient.setClientCountry(client.getClientCountry());
                }
                if (client.getClientCompany() != null) {
                    existingClient.setClientCompany(client.getClientCompany());
                }
                if (client.getCompanyUrl() != null) {
                    existingClient.setCompanyUrl(client.getCompanyUrl());
                }
                if (client.getCompanyWebsite() != null) {
                    existingClient.setCompanyWebsite(client.getCompanyWebsite());
                }

                return existingClient;
            })
            .flatMap(clientRepository::save);
    }

    @Override
    public Flux<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAllBy(pageable);
    }

    public Mono<Long> countAll() {
        return clientRepository.count();
    }

    @Override
    public Mono<Client> findOne(String id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Client : {}", id);
        return clientRepository.deleteById(id);
    }
}
