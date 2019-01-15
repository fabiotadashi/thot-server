package io.redspark.thot.repository;

import io.redspark.thot.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    List<Lead> findAllByDescriptionLike(String description);

}
