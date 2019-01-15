package io.redspark.thot.service;

import io.redspark.thot.controller.dto.LeadDTO;

import java.util.List;

public interface LeadService {

    LeadDTO create(LeadDTO leadDTO);

    LeadDTO findById(Long id);

    List<LeadDTO> findAll();

    LeadDTO update(Long id, LeadDTO leadDTO);

    void delete(Long id);

}
