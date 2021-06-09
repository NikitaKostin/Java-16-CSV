package com.smartsoft.main.repository;

import com.smartsoft.main.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByYmdhBetween(Date fromDate, Date toDate);
    List<Record> findAllBySubtypeIsNotIn(String[] subtype);
}
