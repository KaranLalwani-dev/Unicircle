package com.teamdev.group_up.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tagId;

    @Column(nullable = false, unique = true, length = 50)
    String tagName;

}
