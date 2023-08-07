package com.thinkitive.jhipsterproject.repository;

import com.thinkitive.jhipsterproject.domain.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    Flux<Client> findAllBy(Pageable pageable);
}
