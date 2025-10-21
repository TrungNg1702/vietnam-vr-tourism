package com.vr.tourism.entity;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "destination")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Destination {
    @Id
    private String id;
    private String name;
    private String city;
    private Double lat;
    private Double lng;
    private Boolean has360;
    private String cover;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double rating;
    private Integer price;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties({"destination"})
    private List<Scene> scenes;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties({"destination"})
    private List<Ticket> tickets;
}
