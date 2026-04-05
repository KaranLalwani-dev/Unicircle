package com.teamdev.group_up.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.teamdev.group_up.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Page<Group> findByCreator_UserId(Long creatorId, Pageable pageable);
}
