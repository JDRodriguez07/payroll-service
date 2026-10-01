package com.app.payroll_service.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "contract_type")
public class ContractType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_type_id", nullable = false)
    private Long contractTypeId;

    @OneToMany(mappedBy = "contractType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Contract> contracts;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "requires_end_date", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean requiresEndDate;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
