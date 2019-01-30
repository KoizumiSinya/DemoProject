package com.example.demo_contactasyncqueryhandler.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/02.
 */
public class ContactBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String birthDate;
    private String notes;
    private String webSite;

    private String tell;
    private String phone;
    private String fax;
    private String email;
    private String im;

    private String address;
    private String position;
    private String title;

    private String sortKey;
    public String sortLetters; // 显示数据拼音的首字母
    public SortToken sortToken = new SortToken();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public SortToken getSortToken() {
        return sortToken;
    }

    public void setSortToken(SortToken sortToken) {
        this.sortToken = sortToken;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", notes='" + notes + '\'' +
                ", webSite='" + webSite + '\'' +
                ", tell='" + tell + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", im='" + im + '\'' +
                ", address='" + address + '\'' +
                ", position='" + position + '\'' +
                ", title='" + title + '\'' +
                ", sortKey='" + sortKey + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                ", sortToken=" + sortToken +
                '}';
    }
}
