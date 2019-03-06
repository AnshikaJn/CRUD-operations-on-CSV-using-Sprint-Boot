package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/*@Component
    public class OrderTransactionReceiver {
        @Autowired
        private OrderTransactionRepository transactionRepository;
        @JmsListener(destination = "OrderTransactionQueue", containerFactory = "myFactory")
        public void receiveMessage(OrderTransaction transaction) {
            System.out.println("Received <" + transaction + ">");
            transactionRepository.save(transaction);
        }
    }
*/