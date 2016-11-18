package com.logic.mes.entity.process;


import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.logic.mes.entity.server.ItemCol;
import com.logic.mes.entity.server.ItemKey;

@Table("qx")
public class QxProduct extends ProcessBase {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    private String station;

    @ItemKey()
    private String brickId;
    private String llcps;

    @ItemCol(col = "sjps")
    private String sjcps;
    @ItemCol(col = "hs")
    private String hs;
    @ItemCol(col = "ps")
    private String ps;
    @ItemCol(col = "bb")
    private String bb;
    @ItemCol(col = "yp")
    private String yp;
    @ItemCol(col = "tjqxs")
    private String jjqxs;
    @ItemCol(col = "qxsbs")
    private String sbqxs;
    @ItemCol(col = "qt")
    private String qt;
    private String zj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLlcps() {
        return llcps;
    }

    public void setLlcps(String llcps) {
        this.llcps = llcps;
    }

    public String getSjcps() {
        return sjcps;
    }

    public void setSjcps(String sjcps) {
        this.sjcps = sjcps;
    }

    public String getHs() {
        return hs;
    }

    public void setHs(String hs) {
        this.hs = hs;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb;
    }

    public String getYp() {
        return yp;
    }

    public void setYp(String yp) {
        this.yp = yp;
    }

    public String getJjqxs() {
        return jjqxs;
    }

    public void setJjqxs(String jjqxs) {
        this.jjqxs = jjqxs;
    }

    public String getSbqxs() {
        return sbqxs;
    }

    public void setSbqxs(String sbqxs) {
        this.sbqxs = sbqxs;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }

    public String getBrickId() {
        return brickId;
    }

    public void setBrickId(String brickId) {
        this.brickId = brickId;
    }
}
