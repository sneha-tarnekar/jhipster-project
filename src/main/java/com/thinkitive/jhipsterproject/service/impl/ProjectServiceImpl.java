package com.thinkitive.jhipsterproject.service.impl;

import com.thinkitive.jhipsterproject.domain.Project;
import com.thinkitive.jhipsterproject.repository.ProjectRepository;
import com.thinkitive.jhipsterproject.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Project}.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Mono<Project> save(Project project) {
        log.debug("Request to save Project : {}", project);
        return projectRepository.save(project);
    }

    @Override
    public Mono<Project> update(Project project) {
        log.debug("Request to update Project : {}", project);
        return projectRepository.save(project);
    }

    @Override
    public Mono<Project> partialUpdate(Project project) {
        log.debug("Request to partially update Project : {}", project);

        return projectRepository
            .findById(project.getId())
            .map(existingProject -> {
                if (project.getName() != null) {
                    existingProject.setName(project.getName());
                }
                if (project.getClientName() != null) {
                    existingProject.setClientName(project.getClientName());
                }
                if (project.getIsUpsale() != null) {
                    existingProject.setIsUpsale(project.getIsUpsale());
                }
                if (project.getCountry() != null) {
                    existingProject.setCountry(project.getCountry());
                }
                if (project.getClientCompany() != null) {
                    existingProject.setClientCompany(project.getClientCompany());
                }

                return existingProject;
            })
            .flatMap(projectRepository::save);
    }

    @Override
    public Flux<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectRepository.findAllBy(pageable);
    }

    public Mono<Long> countAll() {
        return projectRepository.count();
    }

    @Override
    public Mono<Project> findOne(String id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Project : {}", id);
        return projectRepository.deleteById(id);
    }
}
