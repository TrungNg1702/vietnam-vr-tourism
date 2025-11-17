package com.vr.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data  // Lombok annotation để tạo getter/setter
@NoArgsConstructor
@AllArgsConstructor

public class TicketDTO {
    private String code;
    private String name;
    private Integer price;
    private String duration;

    private String destinationId;
    private List<TicketSlotDTO> slots;
}
