package com.app.payroll_service.models;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
@Table(name = "vacation")
public class Vacation {

    /**
     * Primary key for the vacation table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id", nullable = false)
    private Long vacationId;

    /**
     * Identifier of the user who is taking the vacation.
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Start date and time of the vacation period.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    /**
     * End date and time of the vacation period.
     */
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    /**
     * Number of vacation days taken.
     */
    @Column(name = "taken_days", nullable = false)
    private int takenDays;

    /**
     * Current status of the vacation request (e.g., approved, pending, rejected).
     */
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * Timestamp indicating when the vacation record was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update to the vacation record.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
