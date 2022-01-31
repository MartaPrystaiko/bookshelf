package com.learnjava.bookshelf.mapper;

import com.learnjava.bookshelf.dto.UserDto;
import com.learnjava.bookshelf.entity.User;
import com.learnjava.bookshelf.service.UserServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
//        (componentModel = "spring", uses = PasswordEncoderMapper.class)//, imports = UserServiceImpl.class)
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

//    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    default User userDtoToUser(UserDto userDto){
         PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( passwordEncoder.encode( userDto.getPassword() ) );
        user.setUsername( userDto.getUsername() );
        user.setRole( userDto.getRole() );
        user.setEmail( userDto.getEmail() );

        return user;
    }

//    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    default UserDto userToUserDto(User user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setPassword( passwordEncoder.encode( user.getPassword() ) );
        userDto.setUsername( user.getUsername() );
        userDto.setEmail( user.getEmail() );
        userDto.setRole( user.getRole() );

        return userDto;
    }
}


//@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class, imports = SecurityUtils.class)
//public interface UserMapper {
//
//    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
//    @Mapping(target = "modifiedBy", expression = "java(SecurityUtils.getCurrentUserId())")
//    User mapUser(UserDto dto);
//}