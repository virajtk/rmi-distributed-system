package com.example.service;

import com.example.entities.Alarm;
import com.example.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    public void createAlarm(Alarm a) throws Exception{
        alarmRepository.save(a);
    }

    public Alarm findByAlarmId(int id) throws Exception {
        return alarmRepository.findById(id);
    }

    public ArrayList<Alarm> findAllAlarms() throws Exception {
        return alarmRepository.findAll();
    }
}
