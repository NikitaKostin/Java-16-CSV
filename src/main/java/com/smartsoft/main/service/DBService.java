package com.smartsoft.main.service;

import com.smartsoft.main.model.Record;
import com.smartsoft.main.model.RecordCSV;
import com.smartsoft.main.repository.RecordRepository;
import com.smartsoft.main.view.SSOIDAndFormIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DBService {

    private RecordRepository recordRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public DBService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Transactional
    public void addAll(List<RecordCSV> records) {
        records.forEach(r -> entityManager.persist(r));
        //TODO Not sure, that all this operations really needed
        entityManager.flush();
        entityManager.clear();
        entityManager.close();
    }

    public void deleteAll() {
        recordRepository.deleteAll();
    }

    public List<Record> unfinishedForms() {
        return recordRepository.findAllBySubtypeIsNotIn(new String[]{"done", "send", "success"});
    }

    public HashMap<String, Integer> topForms() {
        var hashMap = new HashMap<String, Integer>();
        recordRepository
                .findAll()
                .stream()
                .filter(r -> r.getFormid() != null && !r.getFormid().isEmpty())
                .forEach(record -> hashMap.put(record.getFormid(), hashMap.getOrDefault(record.getFormid(), 0) + 1));

        return hashMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public ArrayList<SSOIDAndFormIDs> formsByTime(Date fromDate, Date toDate) {
        var hashMap = new HashMap<String, ArrayList<String>>();
        recordRepository
                .findAllByYmdhBetween(fromDate, toDate)
                .stream()
                .filter(record -> record.getFormid() != null && !record.getFormid().isEmpty())
                .forEach(record -> {
                    if (hashMap.containsKey(record.getSsoid())) {
                        hashMap.get(record.getSsoid()).add(record.getFormid());
                    } else {
                        hashMap.put(record.getSsoid(), new ArrayList<String>() {{
                            add(record.getFormid());
                        }});
                    }
                });

        return hashMap
                .entrySet()
                .stream()
                .map(record -> new SSOIDAndFormIDs(record.getKey(), record.getValue()))
                .collect(Collectors.toCollection(() -> new ArrayList<>(hashMap.size())));
    }
}

