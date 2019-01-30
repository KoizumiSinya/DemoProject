package javaproject.listfristloaddemo;

/**
 * @author Sinya
 * @date 2018/09/16. 14:20
 * @edithor
 * @date
 */
public class Bean {
    private String name;
    private int type;

    public Bean() {
    }

    public Bean(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Bean{" + "name='" + name + '\'' + ", type=" + type + '}';
    }
}
