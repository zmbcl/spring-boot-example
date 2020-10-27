package com.neo.web;

import java.util.List;

import com.neo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.model.User;
import com.neo.mapper.UserMapper;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUsers")
	public List<User> getUsers() {
		List<User> users= userService.getAll();
		return users;
	}
	
    @RequestMapping("/getUser")
    public User getUser(Long id) {
    	User user= userService.getOne(id);
        return user;
    }
    
    @RequestMapping("/add")
    public void save(User user) {
    	userService.insert(user);
    }
    
    @PostMapping(value="update")
    @Transactional
    public void update(@RequestBody User user) {
    	userService.update(user);
    	user.setId(10000L);
        userService.updataUnTransactional(user);
//        int i = 1/0;
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
    	userService.delete(id);
    }
    
    
}