package com.example.repository;


import com.example.entity.TestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDataRepository extends JpaRepository<TestData, Long> {
}
