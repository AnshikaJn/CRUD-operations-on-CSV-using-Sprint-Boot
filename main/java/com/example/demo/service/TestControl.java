package com.example.demo.service;
//package com.opencsv.*;
import com.example.demo.model.Csv;
import com.example.demo.repository.CsvRepository;
import com.opencsv.CSVReader;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.io.File;
import java.util.*;

@Service
public class TestControl {
    public List<Csv> getListOfCSV() {


        ArrayList<String> mylist = new ArrayList<String>();
        ArrayList<Csv> dataList = new ArrayList<>();
        mylist.add("Paytm");
        mylist.add("Phonepe");
        mylist.add("Paypal");
        mylist.add("SecurePay");
        mylist.add("NEFT");
        mylist.add("IMFS");
        int c = 1;
        for (int j = 1; j <= 10; j++) {
            long acc = (int) (Math.random() * ((150000 - 100000) + 1)) + 100000;
            Collections.shuffle(mylist);
            Csv csv = new Csv();
            csv.setId(c);
            csv.setAccountNo((int) acc);
            csv.setDescription(mylist.get(0));
            dataList.add(csv);
            c++;
            }
            return dataList;

    }


    @Autowired
    private CsvRepository abcCsvRepository;

    public List<Csv> saveListOfCsv() {

        List<Csv> data = getListOfCSV();
        System.out.println(data.size());
        System.out.println("function running");
        return (List<Csv>) abcCsvRepository.saveAll(data);
    }
    @Autowired

    public List<Csv> getTableCsv(){
        return (List<Csv>) abcCsvRepository.findAll();
    }
    public void deleteTableCsv(Integer id){

        abcCsvRepository.deleteById(id);
    }

    public Csv updateTableCsv(Csv cv, Integer id) throws Exception {
        Optional<Csv> csvOptional = abcCsvRepository.findById(id);

            // Throw an object of user defined exception
            if (!csvOptional.isPresent())
                throw new MyException("not present");

        cv.setId(id);
        abcCsvRepository.save(cv);
        return cv;

        }
    private final static String QUEUE_NAME = "hello";
        public String send(String text) throws Exception{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                channel.basicPublish("", QUEUE_NAME, null, text.getBytes("UTF-8"));
                return "SENT";
            }catch(Exception e)
            {
                e.printStackTrace();
                return "NOT SENT";
            }

     }


    private final static String QUEUE_NM = "csvFileQueue";
    public String sendFile(MultipartFile filecsv) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NM, false, false, false, null);
            byte [] byteArr=filecsv.getBytes();


            channel.basicPublish("", QUEUE_NM, null, byteArr);
     //       saveFile();
            return "SENT";

        }catch(Exception e)
        {
            e.printStackTrace();
            return "NOT SENT";
        }

    }

   /* @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public Email receiveMess(Email email) {
    //  @JmsListener(destination = "mailbox", containerFactory = "myFactory")
     System.out.println("Received <" + email + ">");
        return email;
    }
   /* public void saveFile() throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NM, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                byte[] byteArray = delivery.getBody();
                FileOutputStream fos = new FileOutputStream(new File("E:/test.csv"));
                fos.write(byteArray);
                FileReader filereader = new FileReader("E:/test.csv");
                CSVReader csvReader = new CSVReader(filereader);
                List<String[]> allData = csvReader.readAll();
                List<Csv> data=new ArrayList<>();
                 for (String[] row : allData) {
                    Csv csv = new Csv();
                    csv.setAccountNo(Integer.parseInt(row[1]));
                    csv.setDescription(row[2]);
                    data.add(csv);

                }
                abcCsvRepository.saveAll(data);
            };
            channel.basicConsume(QUEUE_NM, true, deliverCallback, consumerTag -> { });
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("NOT RECEIVED");
        }

        }
*/
}

