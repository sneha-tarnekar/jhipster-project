package com.thinkitive.jhipsterproject.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Client.
 */
@Document(collection = "client")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("client_name")
    private String clientName;

    @Field("client_country")
    private String clientCountry;

    @Field("client_company")
    private String clientCompany;

    @Field("company_url")
    private String companyUrl;

    @Field("company_website")
    private String companyWebsite;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Client id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientName() {
        return this.clientName;
    }

    public Client clientName(String clientName) {
        this.setClientName(clientName);
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientCountry() {
        return this.clientCountry;
    }

    public Client clientCountry(String clientCountry) {
        this.setClientCountry(clientCountry);
        return this;
    }

    public void setClientCountry(String clientCountry) {
        this.clientCountry = clientCountry;
    }

    public String getClientCompany() {
        return this.clientCompany;
    }

    public Client clientCompany(String clientCompany) {
        this.setClientCompany(clientCompany);
        return this;
    }

    public void setClientCompany(String clientCompany) {
        this.clientCompany = clientCompany;
    }

    public String getCompanyUrl() {
        return this.companyUrl;
    }

    public Client companyUrl(String companyUrl) {
        this.setCompanyUrl(companyUrl);
        return this;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getCompanyWebsite() {
        return this.companyWebsite;
    }

    public Client companyWebsite(String companyWebsite) {
        this.setCompanyWebsite(companyWebsite);
        return this;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", clientCountry='" + getClientCountry() + "'" +
            ", clientCompany='" + getClientCompany() + "'" +
            ", companyUrl='" + getCompanyUrl() + "'" +
            ", companyWebsite='" + getCompanyWebsite() + "'" +
            "}";
    }
}
