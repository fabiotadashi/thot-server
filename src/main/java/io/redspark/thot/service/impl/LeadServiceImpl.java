package io.redspark.thot.service.impl;

import io.redspark.thot.controller.dto.CreateLeadDTO;
import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.exception.NotFoundException;
import io.redspark.thot.model.Lead;
import io.redspark.thot.model.User;
import io.redspark.thot.repository.LeadRepository;
import io.redspark.thot.repository.UserRepository;
import io.redspark.thot.service.LeadService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadServiceImpl implements LeadService {

    private LeadRepository leadRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public LeadServiceImpl(LeadRepository leadRepository,
                           UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.leadRepository = leadRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LeadDTO create(CreateLeadDTO createLeadDTO) {
        Lead lead = modelMapper.map(createLeadDTO, Lead.class);
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
    public List<LeadDTO> findAll(String description) {
        List<Lead> leadList;
        if(description != null && !description.isEmpty()){
            leadList = leadRepository.findAllByDescriptionLike("%"+description+"%");
        }else {
            leadList = leadRepository.findAll();
        }
        return leadList
                .stream()
                .map(lead -> modelMapper.map(lead, LeadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LeadDTO update(Long id, CreateLeadDTO createLeadDTO) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        lead.setDescription(createLeadDTO.getDescription());
        lead.setCompany(createLeadDTO.getCompany());
        lead.setStatus(createLeadDTO.getStatus());

        User vendor = userRepository.findById(createLeadDTO.getVendorId())
                .orElseThrow(NotFoundException::new);

        lead.setVendor(vendor);

        lead = leadRepository.save(lead);
        return modelMapper.map(lead, LeadDTO.class);
    }

    @Override
    public void delete(Long id) {
        leadRepository.deleteById(id);
    }
}
