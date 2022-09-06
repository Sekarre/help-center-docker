package com.sekarre.helpcenterauth.domain;
import com.sekarre.helpcenterauth.domain.enums.RoleName;
import com.sekarre.helpcenterauth.domain.enums.Specialization;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @Builder.Default
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
