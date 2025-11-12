package com.vr.tourism.dto;

import java.util.List;

public class DestinationDTO {
    private String id;
    private String name;
    private String city;
    private Double lat;
    private Double lng;
    private Boolean has360;
    private String cover;
    private String description;
    private Double rating;
    private Integer price;

    private List<SceneDTO> scenes;
    private List<TicketDTO> tickets;
    private List<HighlightDTO> highlights;
    private List<TagDTO> tags;
}
