package io.redspark.thot.model;

import io.redspark.thot.controller.dto.LeadDTO;
import io.redspark.thot.enums.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TB_LEAD")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String company;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private User vendor;

    @Column
    @Enumerated(EnumType.STRING)
    private LeadStatus leadStatus;

}
