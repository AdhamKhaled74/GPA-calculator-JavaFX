package com.example.demo;


import com.example.project1.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Temp {

    String status;
    List<Data[]> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data[]> getData() {
        return data;
    }

    public void setData(List<Data[]> data) {
        this.data=data;
        }
}