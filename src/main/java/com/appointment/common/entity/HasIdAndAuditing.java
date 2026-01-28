package com.appointment.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class HasIdAndAuditing implements Persistable<Long> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(index = 1)
    protected Long id;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    @JsonIgnore
    protected Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @JsonIgnore
    @LastModifiedDate
    protected Instant updatedAt;

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    protected String createdBy;

    @Column(name = "modified_by", nullable = false)
    @JsonIgnore
    @LastModifiedBy
    protected String modifiedBy;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return this.getId() == null;
    }


}
