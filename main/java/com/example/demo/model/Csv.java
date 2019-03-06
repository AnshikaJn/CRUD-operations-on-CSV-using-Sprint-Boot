package com.example.demo.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "csv")
public class Csv {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_no")
    private Integer accountNo;

    @Column(name = "description")
    private String description;

    public Csv() {

    }


        public void setId (Integer id)
        {
            this.id = id;
        }
        public void setAccountNo (Integer accountNo)
        {
            this.accountNo = accountNo;
        }
        public void setDescription (String description)
        {
            this.description = description;
        }
        public Integer getId ()
        {
            return id;
        }
        public Integer getAccountNo()
        {
            return accountNo;
        }
        public String getDescription ()
        {
            return description;
        }
    }
