package jp.sinya.tallybook.model;

/**
 * @author Koizumi Sinya
 * @date 2018/01/02. 22:26
 * @edithor
 * @date
 */
public class Tally extends BaseBean {
    private String title;
    private String date;
    private float cost;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Tally() {
    }

    public Tally(String title, String date, float cost) {
        this.title = title;
        this.date = date;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Tally{" + "title='" + title + '\'' + ", date='" + date + '\'' + ", cost=" + cost + '}';
    }
}
