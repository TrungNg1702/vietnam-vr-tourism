package com.vr.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Lombok annotation để tạo getter/setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;
    private String tag;
    private String destinationId;
}
