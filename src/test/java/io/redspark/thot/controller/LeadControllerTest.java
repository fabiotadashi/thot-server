package io.redspark.thot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.redspark.thot.ThotApplicationTests;
import io.redspark.thot.controller.dto.CreateLeadDTO;
import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.enums.LeadStatus;
import io.redspark.thot.model.Lead;
import io.redspark.thot.repository.LeadRepository;
import io.redspark.thot.service.impl.LeadServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LeadControllerTest extends ThotApplicationTests {

    @Autowired
    private LeadRepository leadRepository;

    @Test
    public void shouldReturnActiveLeads() throws Exception {

        Lead lead = new Lead();
        lead.setActive(true);
        lead.setVendor(null);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingresso");

        leadRepository.save(lead);

        Lead lead1 = new Lead();
        lead1.setActive(false);
        lead1.setVendor(null);
        lead1.setCompany("BMB");
        lead1.setLeadStatus(LeadStatus.HOT);
        lead1.setDescription("Design Spark");

        leadRepository.save(lead);

        MvcResult result = mvc.perform(get("/leads")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse()
                .getContentAsString();

        LeadDTO[] leadDTOs = objectMapper.readValue(json, LeadDTO[].class);

        Assert.assertEquals(leadDTOs.length, 1);
        Assert.assertNotNull(leadDTOs[0].getId());
        Assert.assertEquals(leadDTOs[0].getDescription(), "Projeto Ingresso");
        Assert.assertEquals(leadDTOs[0].getCompany(), "Sesc");
        Assert.assertEquals(leadDTOs[0].getLeadStatus(), LeadStatus.HOT);
    }

    @Test
    public void shouldReturnLeadById() throws Exception {

        Lead lead = new Lead();
        lead.setActive(true);
        lead.setVendor(null);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingresso");

        leadRepository.save(lead);

        Lead lead1 = new Lead();
        lead1.setActive(true);
        lead1.setVendor(null);
        lead1.setCompany("BMB");
        lead1.setLeadStatus(LeadStatus.COLD);
        lead1.setDescription("Design Spark");

        Lead savedLead1 = leadRepository.save(lead1);

        mvc.perform(get(String.format("/leads/%d", 99999999))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        MvcResult result = mvc.perform(get(String.format("/leads/%d", savedLead1.getId()))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse()
                .getContentAsString();

        LeadDTO leadDTO = objectMapper.readValue(json, LeadDTO.class);

        Assert.assertEquals(leadDTO.getId(), savedLead1.getId());
        Assert.assertEquals(leadDTO.getDescription(), savedLead1.getDescription());
        Assert.assertEquals(leadDTO.getCompany(), savedLead1.getCompany());
        Assert.assertEquals(leadDTO.getLeadStatus(), savedLead1.getLeadStatus());
    }

    @Test
    public void shouldCreateLeadForVendor() throws Exception {
        CreateLeadDTO createLeadDTO = CreateLeadDTO.builder()
                .company("Sesc")
                .description("Android App")
                .leadStatus(LeadStatus.COLD)
                .build();

        mvc.perform(post("/leads")
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .content(objectMapper.writeValueAsString(createLeadDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        MvcResult result = mvc.perform(post("/leads")
                .header(HttpHeaders.AUTHORIZATION, getVendorJwtToken())
                .content(objectMapper.writeValueAsString(createLeadDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse()
                .getContentAsString();
        LeadDTO leadDTO = objectMapper.readValue(json, LeadDTO.class);
        Assert.assertNotNull(leadDTO.getId());
        Assert.assertEquals(leadDTO.getDescription(), "Android App");
        Assert.assertEquals(leadDTO.getCompany(), "Sesc");
        Assert.assertEquals(leadDTO.getLeadStatus(), LeadStatus.COLD);

        List<Lead> leadList = leadRepository.findAll();
        Assert.assertEquals(leadList.size(), 1);
    }

    @Test
    public void shouldDelete() throws Exception {
        Lead lead = new Lead();
        lead.setActive(true);
        lead.setVendor(null);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingresso");

        Lead savedLead = leadRepository.save(lead);

        Lead lead1 = new Lead();
        lead1.setActive(true);
        lead1.setVendor(null);
        lead1.setCompany("BMB");
        lead1.setLeadStatus(LeadStatus.COLD);
        lead1.setDescription("Design Spark");

        Lead savedLead1 = leadRepository.save(lead1);

        mvc.perform(delete(String.format("/leads/%d", 99999))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        MvcResult result = mvc.perform(delete(String.format("/leads/%d", savedLead1.getId()))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse()
                .getContentAsString();

        LeadDTO leadDTO = objectMapper.readValue(json, LeadDTO.class);

        Assert.assertEquals(leadDTO.getId(), savedLead1.getId());
        Assert.assertEquals(leadDTO.getDescription(), savedLead1.getDescription());
        Assert.assertEquals(leadDTO.getCompany(), savedLead1.getCompany());
        Assert.assertEquals(leadDTO.getLeadStatus(), savedLead1.getLeadStatus());

        List<Lead> findAllLeads = leadRepository.findAll();
        Assert.assertEquals(findAllLeads.size(), 2);

        List<Lead> findAllActiveLeads = leadRepository.findAllByActiveTrue();
        Assert.assertEquals(findAllActiveLeads.size(), 1);
    }

    @Test
    public void shouldUpdate() throws Exception {
        Lead lead = new Lead();
        lead.setActive(true);
        lead.setVendor(null);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingresso");

        Lead savedLead = leadRepository.save(lead);

        CreateLeadDTO createLeadDTO = CreateLeadDTO.builder()
                .company("Sesc SP")
                .leadStatus(LeadStatus.MEDIUM)
                .description("Projeto Ingresso")
                .build();

        MvcResult result = mvc.perform(put(String.format("/leads/%d", savedLead.getId()))
                .content(objectMapper.writeValueAsString(createLeadDTO))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse()
                .getContentAsString();

        LeadDTO leadDTO = objectMapper.readValue(json, LeadDTO.class);

        Assert.assertEquals(leadDTO.getId(), savedLead.getId());
        Assert.assertEquals(leadDTO.getDescription(), createLeadDTO.getDescription());
        Assert.assertEquals(leadDTO.getCompany(), createLeadDTO.getCompany());
        Assert.assertEquals(leadDTO.getLeadStatus(), createLeadDTO.getLeadStatus());
    }

    @Test
    public void shouldNotStepOver() throws Exception {
        Lead lead = new Lead();
        lead.setActive(true);
        lead.setVendor(null);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingresso");

        Lead savedLead = leadRepository.save(lead);

        CreateLeadDTO createLeadDTO = CreateLeadDTO.builder()
                .company("Sesc")
                .leadStatus(LeadStatus.COLD)
                .description("Projeto Ingresso")
                .build();

        MvcResult result = mvc.perform(put(String.format("/leads/%d", savedLead.getId()))
                .content(objectMapper.writeValueAsString(createLeadDTO))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Exception exception = result.getResolvedException();
        Assert.assertThat(exception, instanceOf(ResponseStatusException.class));

        ResponseStatusException responseStatusException = (ResponseStatusException) exception;

        Assert.assertEquals(responseStatusException.getReason(), LeadServiceImpl.ILLEGAL_LEAD_STATUS);
    }
}
