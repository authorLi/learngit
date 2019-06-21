package com.mycclee.blog.service.impl;

import com.mycclee.blog.beans.User;
import com.mycclee.blog.beans.UserExample;
import com.mycclee.blog.mapper.UserMapper;
import com.mycclee.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User saveOrUpdateUser(User user) {
        Long id = user.getId();
        if (id != null){
            userMapper.updateByPrimaryKey(user);
        }else{
            userMapper.insert(user);
        }
        return user;
    }

    @Override
    public User registerUser(User user) {
        user.setRoles("user");
        System.out.println(user);
        userMapper.insert(user);
        return user;
    }

    @Override
    public void removeUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public List<User> getUserList() {
        UserExample example = new UserExample();
        List<User> list = userMapper.selectByExample(example);
        return list;
    }

    @Override
    public Page<User> listUserByNameLike(String name, Pageable pageable) {
        //模糊查询
        name = "%" + name + "%";
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameLike(name);
        Page<User> users = (Page<User>) userMapper.selectByExample(example);
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        return list.get(0);
    }

}
