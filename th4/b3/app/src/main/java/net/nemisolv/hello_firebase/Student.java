package net.nemisolv.hello_firebase;

public class Student {
    private String name;
    private String id;
    private String className;
    private float score;
    private String imgUrl;

    public Student() {
    }

    public Student(String name, String id, String className, float score) {
        this.name = name;
        this.id = id;
        this.className = className;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


}
