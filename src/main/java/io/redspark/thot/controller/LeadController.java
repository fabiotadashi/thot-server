package io.redspark.thot.controller;

import io.redspark.thot.controller.dto.CreateLeadDTO;
import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public LeadDTO create(@RequestBody CreateLeadDTO createLeadDTO) {
        return leadService.create(createLeadDTO);
    }

    @GetMapping("{id}")
    public LeadDTO findById(@PathVariable Long id) {
        return leadService.findById(id);
    }

    @GetMapping
    public List<LeadDTO> findAll() {
        return leadService.findAll();
    }

    @PutMapping("{id}")
    public LeadDTO update(@PathVariable Long id,
                          @RequestBody CreateLeadDTO createLeadDTO) {
        return leadService.update(id, createLeadDTO);
    }

    @DeleteMapping("{id}")
    public LeadDTO delete(@PathVariable Long id) {
        return leadService.delete(id);
    }

    @GetMapping("page")
    public Page<LeadDTO> findAllPages(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC)
                                                  Pageable pageable){
        return leadService.findAll(pageable);
    }

}
