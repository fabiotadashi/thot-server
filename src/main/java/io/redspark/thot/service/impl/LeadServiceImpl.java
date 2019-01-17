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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
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

        lead.setActive(true);

        User vendor = userRepository.findById(createLeadDTO.getVendorId())
                .orElseThrow(NotFoundException::new);

        lead.setVendor(vendor);

        lead = leadRepository.save(lead);

        LeadDTO dto = modelMapper.map(lead, LeadDTO.class);
        return dto;
    }

    @Override
    public LeadDTO findById(Long id) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        LeadDTO leadDTO = modelMapper.map(lead, LeadDTO.class);

        return leadDTO;
    }

    @Override
    public List<LeadDTO> findAll() {
        return leadRepository.findAllByActiveTrue()
                .stream()
                .map(lead -> modelMapper.map(lead, LeadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LeadDTO update(Long id, CreateLeadDTO createLeadDTO) {

        Lead lead = leadRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        lead.setCompany(createLeadDTO.getCompany());
        lead.setDescription(createLeadDTO.getDescription());
        lead.setLeadStatus(createLeadDTO.getLeadStatus());

        User vendor = userRepository.findById(createLeadDTO.getVendorId())
                .orElseThrow(NotFoundException::new);

        lead.setVendor(vendor);

        leadRepository.save(lead);

        LeadDTO dto = modelMapper.map(lead, LeadDTO.class);
        return dto;
    }

    @Override
    public LeadDTO delete(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        lead.setActive(false);
        lead = leadRepository.save(lead);
        LeadDTO dto = modelMapper.map(lead, LeadDTO.class);
        return dto;
    }

    @Override
    public Page<LeadDTO> findAll(Pageable pageable) {
        return leadRepository.findAllByActiveTrue(pageable)
                .map(lead -> modelMapper.map(lead, LeadDTO.class));
    }
}
