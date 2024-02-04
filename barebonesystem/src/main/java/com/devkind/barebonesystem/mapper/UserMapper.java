package com.devkind.barebonesystem.mapper;

import com.devkind.barebonesystem.dto.auth.UserRegisterDto;
import com.devkind.barebonesystem.dto.user.UpdateUserDto;
import com.devkind.barebonesystem.dto.user.UserDto;
import com.devkind.barebonesystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    User toUser(UserRegisterDto dto);

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    UserDto toUserDto(User user);

    default void mapUpdateUserDtoToUser(UpdateUserDto dto, User user) {
        try {
            if ( dto.getDateOfBirth() != null ) {
                user.setDateOfBirth( new SimpleDateFormat( "yyyy-MM-dd" ).parse( dto.getDateOfBirth() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        user.setFirstname( dto.getFirstname() );
        user.setLastname( dto.getLastname() );
        user.setEmail( dto.getEmail() );
    }
}
