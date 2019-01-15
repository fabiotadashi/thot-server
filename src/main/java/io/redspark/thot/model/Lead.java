package io.redspark.thot.model;

import io.redspark.thot.model.enums.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TB_LEAD")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private String company;

    @ManyToOne
    @JoinColumn(name = "VENDOR_ID")
    private User vendor;

    @Column
    @Enumerated(EnumType.STRING)
    private LeadStatus status;

}
