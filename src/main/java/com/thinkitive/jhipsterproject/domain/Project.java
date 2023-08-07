package com.thinkitive.jhipsterproject.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Project.
 */
@Document(collection = "project")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("client_name")
    private String clientName;

    @Field("is_upsale")
    private Boolean isUpsale;

    @Field("country")
    private String country;

    @Field("client_company")
    private String clientCompany;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Project id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Project name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientName() {
        return this.clientName;
    }

    public Project clientName(String clientName) {
        this.setClientName(clientName);
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Boolean getIsUpsale() {
        return this.isUpsale;
    }

    public Project isUpsale(Boolean isUpsale) {
        this.setIsUpsale(isUpsale);
        return this;
    }

    public void setIsUpsale(Boolean isUpsale) {
        this.isUpsale = isUpsale;
    }

    public String getCountry() {
        return this.country;
    }

    public Project country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getClientCompany() {
        return this.clientCompany;
    }

    public Project clientCompany(String clientCompany) {
        this.setClientCompany(clientCompany);
        return this;
    }

    public void setClientCompany(String clientCompany) {
        this.clientCompany = clientCompany;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", isUpsale='" + getIsUpsale() + "'" +
            ", country='" + getCountry() + "'" +
            ", clientCompany='" + getClientCompany() + "'" +
            "}";
    }
}
