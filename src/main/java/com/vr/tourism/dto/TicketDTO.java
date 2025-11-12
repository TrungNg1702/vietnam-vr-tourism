package com.vr.tourism.dto;

import java.util.List;

public class TicketDTO {
    private String code;
    private String name;
    private Integer price;
    private String duration;

    private String destinationId;
    private List<TicketSlotDTO> slots;
}
