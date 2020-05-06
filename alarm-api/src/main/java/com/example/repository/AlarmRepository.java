package com.example.repository;


import com.example.entities.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AlarmRepository extends JpaRepository<Alarm,Integer> {

    Alarm findById(int id);
    ArrayList<Alarm> findAll();

}
