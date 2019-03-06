package com.example.demo.service;

import com.example.demo.model.Csv;
import com.example.demo.repository.CsvRepository;
import com.opencsv.CSVReader;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileSchedular {
    @Autowired
    private CsvRepository abcCsvRepository;

    private final static String QUEUE_NM = "csvFileQueue";
    @Scheduled(cron = " 0 01,55 16 ? * * ")
    public void saveFile() throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NM, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                byte[] byteArray = delivery.getBody();
                FileOutputStream fos = new FileOutputStream(new File("D:/test.csv"));
                fos.write(byteArray);
                FileReader filereader = new FileReader("D:/test.csv");
                CSVReader csvReader = new CSVReader(filereader);
                List<String[]> allData = csvReader.readAll();
                List<Csv> data=new ArrayList<>();
                 for (String[] row : allData) {
                    Csv csv = new Csv();
                    csv.setAccountNo(Integer.parseInt(row[1]));
                    csv.setDescription(row[2]);
                    System.out.println(csv);
                    data.add(csv);

                }
                 System.out.println("datalist");
                abcCsvRepository.saveAll(data);
            };
            channel.basicConsume(QUEUE_NM, true, deliverCallback, consumerTag -> { });
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("NOT RECEIVED");
        }

        }
}
