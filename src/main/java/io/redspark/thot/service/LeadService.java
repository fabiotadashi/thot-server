package io.redspark.thot.service;

import io.redspark.thot.controller.dto.LeadDTO;

import java.util.List;

public interface LeadService {

    LeadDTO create(LeadDTO leadDTO);

    LeadDTO findById(Integer id);

    List<LeadDTO> findAll();

    LeadDTO update(Integer id, LeadDTO leadDTO);

    void delete(Integer id);

}
