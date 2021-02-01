package com.neo.mapper;

import java.util.List;

import com.neo.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

	List<User> getAll(@Param("delFlag")int delFlag);

	User getOne(Long id);

	void insert(User user);

	boolean update(User user);

	void delete(Long id);

}