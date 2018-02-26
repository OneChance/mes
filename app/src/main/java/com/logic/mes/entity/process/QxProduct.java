package com.logic.mes.entity.process;

import com.logic.mes.entity.server.ItemCol;
import com.logic.mes.entity.server.ItemKey;

public class QxProduct extends ProcessBase {

    private String station;

    @ItemKey()
    private String brickId;
    @ItemCol(col = "qps")
    private String qps;
    @ItemCol(col = "sjcps")
    private String sjcps;
    @ItemCol(col = "tjqxs")
    private String jjqxs;
    @ItemCol(col = "qxsbs")
    private String sbqxs;
    @ItemCol(col = "zqqss")
    private String zqqss;
    @ItemCol(col = "qt")
    private String qt;
    @ItemCol(col = "qxzps")
    private String qxzps;

    private String zj;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getSjcps() {
        return sjcps;
    }

    public void setSjcps(String sjcps) {
        this.sjcps = sjcps;
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

    public String getZqqss() {
        return zqqss;
    }

    public void setZqqss(String zqqss) {
        this.zqqss = zqqss;
    }

    public String getQxzps() {
        return qxzps;
    }

    public void setQxzps(String qxzps) {
        this.qxzps = qxzps;
    }

    public String getQps() {
        return qps;
    }

    public void setQps(String qps) {
        this.qps = qps;
    }
}
