package com.mycclee.blog;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestABC {
    @Test
    public void test(){
        List<String> list = new ArrayList<>();
        String[] strs = "你好棒，666".split(",");
        System.out.println(strs);
    }

    @Test
    public void test1(){

    }
}
