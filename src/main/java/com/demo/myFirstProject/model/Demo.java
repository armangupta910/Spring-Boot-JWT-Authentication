package com.demo.myFirstProject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "demo")
public class Demo {
    @Id
    private String id;
    private String field1;
    private String field2;

    // Constructors, getters, and setters
    public Demo() {
    }

    public Demo(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}

