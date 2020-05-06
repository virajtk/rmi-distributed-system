package com.example.controllers;

import com.example.entities.Alarm;
import com.example.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/alarms")
@CrossOrigin
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createAlarm(@RequestBody Alarm a){

        try {
            alarmService.createAlarm(a);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String successMessage = "Alarm Created !";

        return successMessage;

    }

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<?> findAlarmById(@PathVariable("id")int id){

        Alarm alarm = null;
        try {
            alarm = alarmService.findByAlarmId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(alarm);

    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<?> findAll(){
        ArrayList<Alarm> array = new ArrayList<>();
        try {
            array = alarmService.findAllAlarms();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(array);
    }

}
