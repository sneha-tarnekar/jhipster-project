package com.thinkitive.jhipsterproject.repository;

import com.thinkitive.jhipsterproject.domain.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
    Flux<Project> findAllBy(Pageable pageable);
}
