package com.example.chaoshiapp.bean;

/**
 * Created by Ivan on 15/9/24.
 */
//首页展示通过id，name寻找或通过name寻找
public class Category extends BaseBean {

    private String name;

    public Category() {
    }

    public Category(String name) {

        this.name = name;
    }

    public Category(long id ,String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
