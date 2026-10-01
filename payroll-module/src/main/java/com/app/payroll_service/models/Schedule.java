package com.app.payroll_service.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "schedule")
public class Schedule {

    /**
     * Primary key for the schedule table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    /**
     * Time when the work schedule starts.
     */
    @Column(name = "start_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    /**
     * Time when the work schedule ends.
     */
    @Column(name = "end_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    /**
     * One-to-many relationship with Contract.
     * A schedule can be associated with multiple contracts.
     */
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Contract> contracts;

    /**
     * Timestamp of when the schedule record was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update made to the schedule record.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
