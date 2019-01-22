package io.redspark.thot.model;

import io.redspark.thot.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
@ToString(exclude = {"leadList", "jobTitleList"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "vendor")
    private List<Lead> leadList;

    @ManyToMany
    @JoinTable(name = "TB_USER_JOB_TITLE")
    private List<JobTitle> jobTitleList;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roleList;

    @Column
    private String password;
}
