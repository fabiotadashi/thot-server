package io.redspark.thot.controller;

import io.redspark.thot.ThotApplicationTests;
import io.redspark.thot.controller.dto.CreateLeadDTO;
import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.enums.LeadStatus;
import io.redspark.thot.model.Lead;
import io.redspark.thot.repository.LeadRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LeadControllerTest extends ThotApplicationTests {

    @Autowired
    private LeadRepository leadRepository;

    @Test
    public void shouldReturnActiveLeads() throws Exception {
        Lead lead = new Lead();
        lead.setActive(true);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingressos");

        leadRepository.save(lead);

        Lead lead1 = new Lead();
        lead1.setActive(false);
        lead1.setCompany("BMB");
        lead1.setLeadStatus(LeadStatus.COLD);
        lead1.setDescription("Design Spark");

        leadRepository.save(lead1);

        MvcResult mvcResult = mockMvc.perform(get("/leads")
                .header(HttpHeaders.AUTHORIZATION, super.getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonReturn = mvcResult.getResponse()
                .getContentAsString();

        LeadDTO[] leadDTOS = objectMapper.readValue(jsonReturn, LeadDTO[].class);

        Assert.assertEquals(leadDTOS.length, 1);
        Assert.assertEquals(leadDTOS[0].getDescription(), lead.getDescription());
        Assert.assertEquals(leadDTOS[0].getCompany(), lead.getCompany());
        Assert.assertNotNull(leadDTOS[0].getId());
    }

    @Test
    public void shouldReturnLeadById() throws Exception {
        Lead lead = new Lead();
        lead.setActive(true);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingressos");

        Lead savedLead = leadRepository.save(lead);

        Lead lead1 = new Lead();
        lead1.setActive(true);
        lead1.setCompany("BMB");
        lead1.setLeadStatus(LeadStatus.COLD);
        lead1.setDescription("Design Spark");

        leadRepository.save(lead1);

        mockMvc.perform(get(String.format("/leads/%d", 999999))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        MvcResult mvcResult = mockMvc.perform(get(String.format("/leads/%d", savedLead.getId()))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse()
                .getContentAsString();

        LeadDTO leadDTO = objectMapper.readValue(json, LeadDTO.class);
        Assert.assertEquals(leadDTO.getId(), savedLead.getId());
        Assert.assertEquals(leadDTO.getCompany(), savedLead.getCompany());
        Assert.assertEquals(leadDTO.getDescription(), savedLead.getDescription());
        Assert.assertEquals(leadDTO.getLeadStatus(), savedLead.getLeadStatus());

    }

    @Test
    public void shouldUpdateLead() throws Exception {
        Lead lead = new Lead();
        lead.setActive(true);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingressos");

        Lead savedLead = leadRepository.save(lead);

        CreateLeadDTO createLeadDTO = CreateLeadDTO.builder()
                .company("Sesc SP")
                .description("Projeto Ingressos")
                .leadStatus(LeadStatus.MEDIUM)
                .build();

        MvcResult mvcResult = mockMvc.perform(put(String.format("/leads/%d", savedLead.getId()))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createLeadDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse()
                .getContentAsString();

        LeadDTO leadDTO = objectMapper.readValue(json, LeadDTO.class);

        Assert.assertEquals(leadDTO.getId(), savedLead.getId());
        Assert.assertEquals(leadDTO.getDescription(), createLeadDTO.getDescription());
        Assert.assertEquals(leadDTO.getCompany(), createLeadDTO.getCompany());
        Assert.assertEquals(leadDTO.getLeadStatus(), createLeadDTO.getLeadStatus());
    }

    @Test
    public void shouldNotSkipLeadStatus() throws Exception {
        Lead lead = new Lead();
        lead.setActive(true);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingressos");

        Lead savedLead = leadRepository.save(lead);

        CreateLeadDTO createLeadDTO = CreateLeadDTO.builder()
                .company("Sesc SP")
                .description("Projeto Ingressos")
                .leadStatus(LeadStatus.COLD)
                .build();

        mockMvc.perform(put(String.format("/leads/%d", savedLead.getId()))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createLeadDTO)))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void shouldUpdateLeadWithSameStatus() throws Exception {
        Lead lead = new Lead();
        lead.setActive(true);
        lead.setCompany("Sesc");
        lead.setLeadStatus(LeadStatus.HOT);
        lead.setDescription("Projeto Ingressos");

        Lead savedLead = leadRepository.save(lead);

        CreateLeadDTO createLeadDTO = CreateLeadDTO.builder()
                .company("Sesc SP")
                .description("Projeto Ingressos")
                .leadStatus(LeadStatus.HOT)
                .build();

        mockMvc.perform(put(String.format("/leads/%d", savedLead.getId()))
                .header(HttpHeaders.AUTHORIZATION, getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createLeadDTO)))
                .andExpect(status().isOk());

    }

}
