package com.vr.tourism.repository;

import com.vr.tourism.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByDestinationId(String destinationId);
}
