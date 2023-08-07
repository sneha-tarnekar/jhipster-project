package com.thinkitive.jhipsterproject.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.thinkitive.jhipsterproject.IntegrationTest;
import com.thinkitive.jhipsterproject.domain.Project;
import com.thinkitive.jhipsterproject.repository.ProjectRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProjectResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_UPSALE = false;
    private static final Boolean UPDATED_IS_UPSALE = true;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_COMPANY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Project project;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createEntity() {
        Project project = new Project()
            .name(DEFAULT_NAME)
            .clientName(DEFAULT_CLIENT_NAME)
            .isUpsale(DEFAULT_IS_UPSALE)
            .country(DEFAULT_COUNTRY)
            .clientCompany(DEFAULT_CLIENT_COMPANY);
        return project;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity() {
        Project project = new Project()
            .name(UPDATED_NAME)
            .clientName(UPDATED_CLIENT_NAME)
            .isUpsale(UPDATED_IS_UPSALE)
            .country(UPDATED_COUNTRY)
            .clientCompany(UPDATED_CLIENT_COMPANY);
        return project;
    }

    @BeforeEach
    public void initTest() {
        projectRepository.deleteAll().block();
        project = createEntity();
    }

    @Test
    void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().collectList().block().size();
        // Create the Project
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(project))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProject.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testProject.getIsUpsale()).isEqualTo(DEFAULT_IS_UPSALE);
        assertThat(testProject.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testProject.getClientCompany()).isEqualTo(DEFAULT_CLIENT_COMPANY);
    }

    @Test
    void createProjectWithExistingId() throws Exception {
        // Create the Project with an existing ID
        project.setId("existing_id");

        int databaseSizeBeforeCreate = projectRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(project))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllProjects() {
        // Initialize the database
        projectRepository.save(project).block();

        // Get all the projectList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(project.getId()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].clientName")
            .value(hasItem(DEFAULT_CLIENT_NAME))
            .jsonPath("$.[*].isUpsale")
            .value(hasItem(DEFAULT_IS_UPSALE.booleanValue()))
            .jsonPath("$.[*].country")
            .value(hasItem(DEFAULT_COUNTRY))
            .jsonPath("$.[*].clientCompany")
            .value(hasItem(DEFAULT_CLIENT_COMPANY));
    }

    @Test
    void getProject() {
        // Initialize the database
        projectRepository.save(project).block();

        // Get the project
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, project.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(project.getId()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.clientName")
            .value(is(DEFAULT_CLIENT_NAME))
            .jsonPath("$.isUpsale")
            .value(is(DEFAULT_IS_UPSALE.booleanValue()))
            .jsonPath("$.country")
            .value(is(DEFAULT_COUNTRY))
            .jsonPath("$.clientCompany")
            .value(is(DEFAULT_CLIENT_COMPANY));
    }

    @Test
    void getNonExistingProject() {
        // Get the project
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingProject() throws Exception {
        // Initialize the database
        projectRepository.save(project).block();

        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).block();
        updatedProject
            .name(UPDATED_NAME)
            .clientName(UPDATED_CLIENT_NAME)
            .isUpsale(UPDATED_IS_UPSALE)
            .country(UPDATED_COUNTRY)
            .clientCompany(UPDATED_CLIENT_COMPANY);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedProject.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedProject))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testProject.getIsUpsale()).isEqualTo(UPDATED_IS_UPSALE);
        assertThat(testProject.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testProject.getClientCompany()).isEqualTo(UPDATED_CLIENT_COMPANY);
    }

    @Test
    void putNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, project.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(project))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(project))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(project))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.save(project).block();

        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject.clientName(UPDATED_CLIENT_NAME).isUpsale(UPDATED_IS_UPSALE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProject.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProject.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testProject.getIsUpsale()).isEqualTo(UPDATED_IS_UPSALE);
        assertThat(testProject.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testProject.getClientCompany()).isEqualTo(DEFAULT_CLIENT_COMPANY);
    }

    @Test
    void fullUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.save(project).block();

        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .name(UPDATED_NAME)
            .clientName(UPDATED_CLIENT_NAME)
            .isUpsale(UPDATED_IS_UPSALE)
            .country(UPDATED_COUNTRY)
            .clientCompany(UPDATED_CLIENT_COMPANY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProject.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testProject.getIsUpsale()).isEqualTo(UPDATED_IS_UPSALE);
        assertThat(testProject.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testProject.getClientCompany()).isEqualTo(UPDATED_CLIENT_COMPANY);
    }

    @Test
    void patchNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, project.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(project))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(project))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(project))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProject() {
        // Initialize the database
        projectRepository.save(project).block();

        int databaseSizeBeforeDelete = projectRepository.findAll().collectList().block().size();

        // Delete the project
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, project.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
