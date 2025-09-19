package com.vr.tourism.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ticket_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TicketSlot {
    @Id
    private String id;

    @Column(name = "slot_date")
    private LocalDate slotDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    private Integer capacity;
    private Integer available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_code", referencedColumnName = "code")
    @JsonIgnoreProperties({"slots","destination"})
    private Ticket ticket;
}
