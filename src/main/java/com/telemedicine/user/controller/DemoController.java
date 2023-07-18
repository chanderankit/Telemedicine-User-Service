package com.telemedicine.user.controller;

import com.telemedicine.user.model.dao.TimeSlot;
import com.telemedicine.user.repository.TimeSlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/others")
@Slf4j
public class DemoController {
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @PostMapping("/time-slot")
    public ResponseEntity<?> saveTime(@RequestParam("time") String time, @RequestParam("minute") String minute) {
        List<TimeSlot> list = new ArrayList<>();
        for(int i=0;i<25;i++) {
            for(int j=0;j<=30;j+=30) {
                TimeSlot timeSlot = new TimeSlot();
                if(i==0 && j==0) timeSlot.setTime("00:00");
                else if(i==0) timeSlot.setTime("00:" + j);
                else if(j==0 && i<10) timeSlot.setTime("0" + i+ ":0" +j);
                else if(j==30 && i<10) timeSlot.setTime("0" + i+ ":" +j);
                else if(j==0 && i>9) timeSlot.setTime(0 + i+ ":0" +j);
                else timeSlot.setTime(i+ ":" +j);
                timeSlot = timeSlotRepository.save(timeSlot);
                list.add(timeSlot);
            }
        }

        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }
}
