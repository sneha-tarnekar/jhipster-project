package com.thinkitive.jhipsterproject.web.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.thinkitive.jhipsterproject.IntegrationTest;
import com.thinkitive.jhipsterproject.domain.Client;
import com.thinkitive.jhipsterproject.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ClientResourceIT {

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_URL = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_URL = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_WEBSITE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity() {
        Client client = new Client()
            .clientName(DEFAULT_CLIENT_NAME)
            .clientCountry(DEFAULT_CLIENT_COUNTRY)
            .clientCompany(DEFAULT_CLIENT_COMPANY)
            .companyUrl(DEFAULT_COMPANY_URL)
            .companyWebsite(DEFAULT_COMPANY_WEBSITE);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity() {
        Client client = new Client()
            .clientName(UPDATED_CLIENT_NAME)
            .clientCountry(UPDATED_CLIENT_COUNTRY)
            .clientCompany(UPDATED_CLIENT_COMPANY)
            .companyUrl(UPDATED_COMPANY_URL)
            .companyWebsite(UPDATED_COMPANY_WEBSITE);
        return client;
    }

    @BeforeEach
    public void initTest() {
        clientRepository.deleteAll().block();
        client = createEntity();
    }

    @Test
    void getAllClients() {
        // Initialize the database
        clientRepository.save(client).block();

        // Get all the clientList
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
            .value(hasItem(client.getId()))
            .jsonPath("$.[*].clientName")
            .value(hasItem(DEFAULT_CLIENT_NAME))
            .jsonPath("$.[*].clientCountry")
            .value(hasItem(DEFAULT_CLIENT_COUNTRY))
            .jsonPath("$.[*].clientCompany")
            .value(hasItem(DEFAULT_CLIENT_COMPANY))
            .jsonPath("$.[*].companyUrl")
            .value(hasItem(DEFAULT_COMPANY_URL))
            .jsonPath("$.[*].companyWebsite")
            .value(hasItem(DEFAULT_COMPANY_WEBSITE));
    }

    @Test
    void getClient() {
        // Initialize the database
        clientRepository.save(client).block();

        // Get the client
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, client.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(client.getId()))
            .jsonPath("$.clientName")
            .value(is(DEFAULT_CLIENT_NAME))
            .jsonPath("$.clientCountry")
            .value(is(DEFAULT_CLIENT_COUNTRY))
            .jsonPath("$.clientCompany")
            .value(is(DEFAULT_CLIENT_COMPANY))
            .jsonPath("$.companyUrl")
            .value(is(DEFAULT_COMPANY_URL))
            .jsonPath("$.companyWebsite")
            .value(is(DEFAULT_COMPANY_WEBSITE));
    }

    @Test
    void getNonExistingClient() {
        // Get the client
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }
}
