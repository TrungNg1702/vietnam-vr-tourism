package com.vr.tourism.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TicketSlotDTO {
    private String id;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer capacity;
    private Integer available;

    private String ticketCode;
}
