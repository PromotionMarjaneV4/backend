package com.youcode.marjanv2.Repositories;

import com.youcode.marjanv2.Models.Entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
}
