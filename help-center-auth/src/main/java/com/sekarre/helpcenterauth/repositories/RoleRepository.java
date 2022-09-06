package com.sekarre.helpcenterauth.repositories;

import com.sekarre.helpcenterauth.domain.Role;
import com.sekarre.helpcenterauth.domain.enums.RoleName;
import com.sekarre.helpcenterauth.domain.enums.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

    List<Set<Role>> findAllByName(RoleName name);

    Optional<Role> findByNameAndSpecialization(RoleName name, Specialization specialization);
}
