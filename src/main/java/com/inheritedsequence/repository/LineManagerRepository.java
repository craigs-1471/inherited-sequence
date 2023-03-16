package com.inheritedsequence.repository;

import com.inheritedsequence.entity.LineManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineManagerRepository extends JpaRepository<LineManager, Long> {
}
