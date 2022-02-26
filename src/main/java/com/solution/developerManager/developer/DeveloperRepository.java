package com.solution.developerManager.developer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    void deleteDeveloperByEmail(String email);

    boolean existsDeveloperByEmail(String email);

    Developer findByEmail(String email);
}
