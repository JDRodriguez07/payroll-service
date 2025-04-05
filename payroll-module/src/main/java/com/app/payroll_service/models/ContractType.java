package com.app.payroll_service.models;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "requires_end_date", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean requiresEndDate; // JPA lo mapea automáticamente a 0 o 1 en la BD

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
