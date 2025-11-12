package com.vr.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data  // Lombok annotation để tạo getter/setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketSlotDTO {
    private String id;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer capacity;
    private Integer available;

    private String ticketCode;
}
