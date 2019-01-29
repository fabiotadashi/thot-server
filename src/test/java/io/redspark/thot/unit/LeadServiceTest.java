package io.redspark.thot.unit;

import io.redspark.thot.model.Lead;
import io.redspark.thot.repository.LeadRepository;
import io.redspark.thot.repository.UserRepository;
import io.redspark.thot.service.LeadService;
import io.redspark.thot.service.impl.LeadServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;
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
                modelMapper
        );

        Optional<Lead> optionalLead = Optional.of(new Lead());

        when(leadRepository.findById(1L))
                .thenReturn(optionalLead);

        try{
            leadService.delete(2L);
        } catch (ResponseStatusException exception){
            Assert.assertEquals(exception.getReason(), LeadServiceImpl.LEAD_NOT_FOUND);
        }
    }
}
