package com.example.zhouhui.study.entity;


import com.litesuits.orm.db.annotation.MapCollection;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

import java.util.ArrayList;
import java.util.List;

@Table("tj")
public class TjProduct {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    private String brickId;
    private String length;
    private String weight;
    private String validLength;
    private String bbc;

    @Mapping(Relation.OneToMany)
    @MapCollection(ArrayList.class)
    private List<TjDetail> dList;

    public String getBrickId() {
        return brickId;
    }

    public void setBrickId(String brickId) {
        this.brickId = brickId;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getValidLength() {
        return validLength;
    }

    public void setValidLength(String validLength) {
        this.validLength = validLength;
    }

    public String getBbc() {
        return bbc;
    }

    public void setBbc(String bbc) {
        this.bbc = bbc;
    }

    public List<TjDetail> getdList() {
        return dList;
    }

    public void setdList(List<TjDetail> dList) {
        this.dList = dList;
    }
}
