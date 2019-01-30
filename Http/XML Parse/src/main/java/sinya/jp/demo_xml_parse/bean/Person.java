package sinya.jp.demo_xml_parse.bean;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/24 16:55
 * editor：
 * updateDate：2015/9/24 16:55
 */
public class Person {
    public String name;
    public int age;
    public String work;

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", work='" + work + '\'' +
                '}';
    }
}
