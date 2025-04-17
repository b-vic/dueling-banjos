package com.duelingbanjos.demo.repository;

import com.duelingbanjos.demo.entity.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends CrudRepository<Result, String> {

}
