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

    /**
     * Primary key for the contract_type table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_type_id", nullable = false)
    private Long contractTypeId;

    /**
     * List of contracts associated with this contract type.
     * Marked with @JsonIgnore to avoid circular references during serialization.
     */
    @OneToMany(mappedBy = "contractType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Contract> contracts;

    /**
     * Name of the contract type.
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * Indicates if this contract type requires an end date.
     * Stored in the database as TINYINT(1) (0 or 1).
     */
    @Column(name = "requires_end_date", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean requiresEndDate;

    /**
     * Timestamp when the contract type was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the contract type was last updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
