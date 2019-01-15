package io.redspark.thot.service.impl;

import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.exception.NotFoundException;
import io.redspark.thot.model.Lead;
import io.redspark.thot.repository.LeadRepository;
import io.redspark.thot.service.LeadService;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeadServiceImpl implements LeadService {

    private LeadRepository leadRepository;
    private ModelMapper modelMapper;

    public LeadServiceImpl(LeadRepository leadRepository,
                           ModelMapper modelMapper) {
        this.leadRepository = leadRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LeadDTO create(LeadDTO leadDTO) {
        leadDTO.setId(null);
        Lead lead = modelMapper.map(leadDTO, Lead.class);
        lead = leadRepository.save(lead);
        return modelMapper.map(lead, LeadDTO.class);
    }

    @Override
    public LeadDTO findById(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return modelMapper.map(lead, LeadDTO.class);
    }

    @Override
    public List<LeadDTO> findAll() {
        return leadRepository.findAll()
                .stream()
                .map(lead -> modelMapper.map(lead, LeadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LeadDTO update(Long id, LeadDTO leadDTO) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        lead.setDescription(leadDTO.getDescription());
        lead.setCompany(leadDTO.getCompany());
        lead.setStatus(leadDTO.getStatus());
        lead.setVendor(leadDTO.getVendor());

        lead = leadRepository.save(lead);
        return modelMapper.map(lead, LeadDTO.class);
    }

    @Override
    public void delete(Long id) {
        leadRepository.deleteById(id);
    }
}
