package com.vr.tourism.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hotspots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hotspot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String type;
    private Double yaw;
    private Double pitch;
    private String label;

//    @Column(columnDefinition = "TEXT")
//    private String content;

    // Day se la SceneID
    private Long target;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scene_id")
    @JsonIgnoreProperties({"hotspots","destination"})
    private Scene scene;
}
