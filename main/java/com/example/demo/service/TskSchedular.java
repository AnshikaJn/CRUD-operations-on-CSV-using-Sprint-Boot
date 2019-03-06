package com.example.demo.service;

import com.opencsv.CSVWriter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class TskSchedular {
    @Scheduled(cron = " 0 40,41 16 ? * * ")
    public void cronSchedule() {
        File directory=new File("E:/fol");
        int fileCount=directory.list().length;
        int ab=fileCount+1;
        System.out.println(ab);
        File file = new File("E:/fol/"+ab+"rs.csv");
        ab++;
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            ArrayList<String> mylist = new ArrayList<String>();
            List<String[]> data = new ArrayList<String[]>();
            mylist.add("Paytm");
            mylist.add("Phonepe");
            mylist.add("Paypal");
            mylist.add("SecurePay");
            mylist.add("NEFT");
            mylist.add("IMFS");
            int c=1;
            for(int j=1;j<=50;j++)
            {
                long acc = (int)(Math.random()*((150000-100000)+1))+100000;
                Collections.shuffle(mylist);
                System.out.println(c+","+acc+","+mylist.get(0));
                data.add(new String[] { String.valueOf(c), String.valueOf(acc), mylist.get(0)});
                c++;
            }
            writer.writeAll(data);
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
