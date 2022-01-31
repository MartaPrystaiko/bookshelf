package com.learnjava.bookshelf.service;

import com.learnjava.bookshelf.dao.UserDao;
import com.learnjava.bookshelf.dto.UserDto;
import com.learnjava.bookshelf.entity.User;
import com.learnjava.bookshelf.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }

    @Override
    public String createUser(UserDto userDto) {
        User user = UserMapper.USER_MAPPER.userDtoToUser(userDto);
        userDao.save(user);
        return null;
    }


}
