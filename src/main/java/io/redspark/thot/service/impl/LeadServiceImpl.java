package io.redspark.thot.service.impl;

import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.service.LeadService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeadServiceImpl implements LeadService {

    private List<LeadDTO> leadDTOList = new ArrayList<>();

    @Override
    public LeadDTO create(LeadDTO leadDTO) {
        leadDTOList.add(leadDTO);
        return leadDTO;
    }

    @Override
    public LeadDTO findById(Integer id) {
        return leadDTOList.stream()
                .filter(leadDTO -> leadDTO.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public List<LeadDTO> findAll() {
        return leadDTOList;
    }

    @Override
    public LeadDTO update(Integer id, LeadDTO leadDTO) {
        LeadDTO leadDTOFound = findById(id);
        leadDTOFound.setCompany(leadDTO.getCompany());
        leadDTOFound.setDescription(leadDTO.getDescription());
        leadDTOFound.setStatus(leadDTO.getStatus());
        leadDTOFound.setVendor(leadDTO.getVendor());
        return leadDTOFound;
    }

    @Override
    public void delete(Integer id) {
        LeadDTO leadDTO = findById(id);
        leadDTOList.remove(leadDTO);
    }
}
