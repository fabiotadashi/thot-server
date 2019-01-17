package io.redspark.thot.service;

import io.redspark.thot.controller.dto.CreateLeadDTO;
import io.redspark.thot.controller.dto.LeadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LeadService {

    LeadDTO create(CreateLeadDTO createLeadDTO);

    LeadDTO findById(Long id);

    List<LeadDTO> findAll();

    LeadDTO update(Long id, CreateLeadDTO createLeadDTO);

    LeadDTO delete(Long id);

    Page<LeadDTO> findAll(Pageable pageable);
}
