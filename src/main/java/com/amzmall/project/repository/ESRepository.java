package com.amzmall.project.repository;

import com.amzmall.project.model.ES;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESRepository extends JpaRepository<ES,String> {

}
