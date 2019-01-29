package io.redspark.thot.unit;

import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.enums.LeadStatus;
import io.redspark.thot.model.Lead;
import io.redspark.thot.repository.LeadRepository;
import io.redspark.thot.repository.UserRepository;
import io.redspark.thot.service.LeadService;
import io.redspark.thot.service.impl.LeadServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeadServiceTest {

    @Test
    public void deleteLead() {

        LeadRepository leadRepository = mock(LeadRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        ModelMapper modelMapper = mock(ModelMapper.class);

        LeadService leadService = new LeadServiceImpl(leadRepository,
                userRepository,
                modelMapper);

        try{
            leadService.delete(1L);
        }catch (ResponseStatusException exception){
            Assert.assertEquals(exception.getReason(), LeadServiceImpl.LEAD_NOT_FOUND);
        }

        Lead lead = new Lead();
        lead.setLeadStatus(LeadStatus.COLD);
        lead.setCompany("Sesc");
        lead.setDescription("Projeto ingressos");
        lead.setActive(true);
        lead.setId(1L);

        when(leadRepository.findById(1L))
                .thenReturn(Optional.of(lead));

        leadService.delete(1L);
    }

}
