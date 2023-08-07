package com.thinkitive.jhipsterproject.service;

import com.thinkitive.jhipsterproject.domain.Client;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link Client}.
 */
public interface ClientService {
    /**
     * Save a client.
     *
     * @param client the entity to save.
     * @return the persisted entity.
     */
    Mono<Client> save(Client client);

    /**
     * Updates a client.
     *
     * @param client the entity to update.
     * @return the persisted entity.
     */
    Mono<Client> update(Client client);

    /**
     * Partially updates a client.
     *
     * @param client the entity to update partially.
     * @return the persisted entity.
     */
    Mono<Client> partialUpdate(Client client);

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<Client> findAll(Pageable pageable);

    /**
     * Returns the number of clients available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" client.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<Client> findOne(String id);

    /**
     * Delete the "id" client.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(String id);
}
