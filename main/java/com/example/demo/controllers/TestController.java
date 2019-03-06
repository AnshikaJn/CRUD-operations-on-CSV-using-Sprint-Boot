package com.example.demo.controllers;
import com.example.demo.model.Csv;
import com.example.demo.service.Email;
import com.example.demo.service.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.TestControl;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
//@Controller
public class TestController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @Autowired
    private TestControl testControl;


    //generate arbitary data
    @GetMapping(value = "/data", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Csv>> getCsv() {
        return new ResponseEntity<List<Csv>>(testControl.getListOfCSV(), HttpStatus.OK);

    }


    //save data in database
    @GetMapping(value = "/save", produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<List<Csv>> saveCsv() {
        System.out.println("method running");
        return new ResponseEntity<List<Csv>>(testControl.saveListOfCsv(), HttpStatus.OK);

    }


    //Display all records from database
    @GetMapping(value = "/output", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Csv>> displayCsv() {
        return new ResponseEntity<List<Csv>>(testControl.getTableCsv(), HttpStatus.OK);

    }

    //Update record by ID in database
    @PutMapping("/stud/{id}")
    public ResponseEntity<Csv> updateStudent(@RequestBody Csv cv, @PathVariable(value = "id") Integer id)
            throws Exception {

        return new ResponseEntity<Csv>(testControl.updateTableCsv(cv, id), HttpStatus.OK);
    }

    @PutMapping(value = "/rabbit")
    public ResponseEntity<String> sendmessage(@RequestBody String text, HttpServletRequest request) throws Exception {
        return new ResponseEntity<String>(testControl.send(text), HttpStatus.OK);

    }

    @PostMapping(value = "/rab")
    public ResponseEntity<String> sendCsvFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        System.out.println(file.getName());
        return new ResponseEntity<String>(testControl.sendFile(file), HttpStatus.OK);

    }

    //Delete record by ID from database
    @RequestMapping(method = {RequestMethod.DELETE}, produces = TEXT_PLAIN_VALUE, value = "/students/{id}")
    public ResponseEntity<String> deleteCsv(@PathVariable(value = "id") String id) {
        testControl.deleteTableCsv(Integer.parseInt(id));
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping(value = "/jms")
    public void sendMessage(@RequestBody Email email) {
        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", email);
        System.out.println("Sent");

    }
}