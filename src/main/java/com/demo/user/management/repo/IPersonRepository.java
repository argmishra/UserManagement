package com.demo.user.management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.user.management.model.Person;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {

}
