package com.vr.tourism.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data  // Lombok annotation để tạo getter/setter
@NoArgsConstructor
@AllArgsConstructor

public class DestinationDTO {
    private Long id;

    @NotNull(message = "Name must not be null")
    private String name;

    @NotNull(message = "City must not be null")
    private String city;

    @Min(value = 0, message = "Latitude must be greater than or equal to zero")
    private Double lat;

    @Min(value = 0, message = "Longitude must be greater than or equal to zero")
    private Double lng;

    private Boolean has360;

    private String cover;

    private String description;

    private Double rating;

    @NotNull(message = "Price must not be null")
    @Min(value = 1, message = "Price must be greater than or equal to one")
    private Integer price;

    private List<SceneDTO> scenes;
    private List<TicketDTO> tickets;
    private List<HighlightDTO> highlights;
    private List<TagDTO> tags;
}
