package io.redspark.thot.service;

import io.redspark.thot.controller.dto.CreateLeadDTO;
import io.redspark.thot.controller.dto.LeadDTO;

import java.util.List;

public interface LeadService {

    LeadDTO create(CreateLeadDTO createLeadDTO);

    LeadDTO findById(Long id);

    List<LeadDTO> findAll(String description);

    LeadDTO update(Long id, CreateLeadDTO createLeadDTO);

    void delete(Long id);

}
