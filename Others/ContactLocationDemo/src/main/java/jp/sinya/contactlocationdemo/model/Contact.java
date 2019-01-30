package jp.sinya.contactlocationdemo.model;

/**
 * @author Koizumi Sinya
 * @date 2018/01/01. 21:22
 * @edithor
 * @date
 */
public class Contact {
    private String mts;
    private String province;
    private String catName;
    private String telString;
    private String areaVid;
    private String ispVid;
    private String carrier;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMts() {
        return mts;
    }

    public void setMts(String mts) {
        this.mts = mts;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getTelString() {
        return telString;
    }

    public void setTelString(String telString) {
        this.telString = telString;
    }

    public String getAreaVid() {
        return areaVid;
    }

    public void setAreaVid(String areaVid) {
        this.areaVid = areaVid;
    }

    public String getIspVid() {
        return ispVid;
    }

    public void setIspVid(String ispVid) {
        this.ispVid = ispVid;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @Override
    public String toString() {
        return "Contact{" + "mts='" + mts + '\'' + ", province='" + province + '\'' + ", catName='" + catName + '\'' + ", telString='" + telString + '\'' + ", areaVid='" + areaVid + '\'' + ", " +
                "ispVid='" + ispVid + '\'' + ", carrier='" + carrier + '\'' + '}';
    }
}
