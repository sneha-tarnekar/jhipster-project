package com.thinkitive.jhipsterproject.service;

import com.thinkitive.jhipsterproject.domain.Project;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link Project}.
 */
public interface ProjectService {
    /**
     * Save a project.
     *
     * @param project the entity to save.
     * @return the persisted entity.
     */
    Mono<Project> save(Project project);

    /**
     * Updates a project.
     *
     * @param project the entity to update.
     * @return the persisted entity.
     */
    Mono<Project> update(Project project);

    /**
     * Partially updates a project.
     *
     * @param project the entity to update partially.
     * @return the persisted entity.
     */
    Mono<Project> partialUpdate(Project project);

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<Project> findAll(Pageable pageable);

    /**
     * Returns the number of projects available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" project.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<Project> findOne(String id);

    /**
     * Delete the "id" project.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(String id);
}
