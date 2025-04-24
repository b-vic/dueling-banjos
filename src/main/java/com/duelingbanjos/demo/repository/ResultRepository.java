package com.duelingbanjos.demo.repository;

import com.duelingbanjos.demo.entity.PerfResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends CrudRepository<PerfResult, String> {

}
