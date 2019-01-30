package jp.sinya.mvpdemo3.mvp.model;

import java.util.Comparator;

/**
 * @author Koizumi Sinya
 * @date 2018/01/23. 17:22
 * @edithor
 * @date
 */
public class Contact {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String gender;
    private String phone;
    private String address;
    private String job;
    private String avatar;
    private long date;

    public Contact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static class DateComparator implements Comparator<Contact> {
        public int compare(Contact o1, Contact o2) {
            if (o1.date > o2.date) {
                return -1;
            } else if (o1.date == o2.date) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    @Override
    public String toString() {
        return "Contact{" + "id=" + id + ", first_name='" + first_name + '\'' + ", last_name='" + last_name + '\'' + ", email='" + email + '\'' + ", gender='" + gender + '\'' + ", phone='" + phone
                + '\'' + ", address='" + address + '\'' + ", job='" + job + '\'' + ", avatar='" + avatar + '\'' + ", date=" + date + '}';
    }
}
