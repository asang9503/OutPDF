/**
 * Author:阿桑
 * Date:2019/7/4/004
 * Description: 内部使用严禁外泄
 */
public class ForPDF {
    private String name;

    private int age ;

    private float height;

    private String adress;

    private String sex;

    private String jj;

    public ForPDF() {
    }

    public ForPDF(String name, int age, float height, String adress, String sex, String jj) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.adress = adress;
        this.sex = sex;
        this.jj = jj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJj() {
        return jj;
    }

    public void setJj(String jj) {
        this.jj = jj;
    }
}
