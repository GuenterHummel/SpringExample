package com.gh.demoserver.dao;


import com.gh.demoserver.entities.Officer;
import com.gh.demoserver.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficerRepository extends JpaRepository<Officer, Integer> {
    List<Officer> findAllByLastNameContainingAndRank(String lastName, Rank rank);
}
