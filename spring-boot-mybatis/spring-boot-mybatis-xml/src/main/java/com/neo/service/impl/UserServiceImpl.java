package com.neo.service.impl;

import com.neo.mapper.UserMapper;
import com.neo.model.User;
import com.neo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 11:09 上午 2020/10/27
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public List<User> getAll(int delFlag) {
        return userMapper.getAll(delFlag);
    }

    @Override
    public User getOne(Long id) {
        return userMapper.getOne(id);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }


    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void updataUnTransactional(User user) {
        userMapper.update(user);
    }


    @Override
    public void delete(Long id) {
        userMapper.delete(id);
    }


}
