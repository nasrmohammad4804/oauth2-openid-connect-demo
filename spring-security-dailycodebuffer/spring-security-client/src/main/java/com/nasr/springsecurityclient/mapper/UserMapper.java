package com.nasr.springsecurityclient.mapper;

import com.nasr.springsecurityclient.base.mapper.BaseMapper;
import com.nasr.springsecurityclient.entity.User;
import com.nasr.springsecurityclient.model.UserModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper extends BaseMapper<User, UserModel> {
}
