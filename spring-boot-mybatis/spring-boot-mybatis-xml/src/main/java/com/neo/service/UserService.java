package com.neo.service;

import com.neo.model.User;

import java.util.List;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 11:09 上午 2020/10/27
 */
public interface UserService {
    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void updataUnTransactional(User user);

    void delete(Long id);
}
