package io.redspark.thot.controller;

import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("leads")
public class LeadController {

    private final LeadService leadService;

    @Autowired
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public LeadDTO create(@RequestBody LeadDTO leadDTO) {
        return leadService.create(leadDTO);
    }

    @GetMapping("{id}")
    public LeadDTO findById(@PathVariable Integer id) {
        return leadService.findById(id);
    }

    @GetMapping
    public List<LeadDTO> findAll() {
        return leadService.findAll();
    }

    @PutMapping("{id}")
    public LeadDTO update(@PathVariable Integer id,
                          @RequestBody LeadDTO leadDTO) {
        return leadService.update(id, leadDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        leadService.delete(id);
    }

}
