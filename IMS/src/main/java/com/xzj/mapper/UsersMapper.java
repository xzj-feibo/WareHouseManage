package com.xzj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzj.model.Users;
import com.xzj.resp.UserResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    @Select("select * from user1 where account = #{account} and password = #{password}")
    UserResp login(@Param("account") String account, @Param("password") String password);

    @Update("update user1 set password = '123456' where id=#{userId}")
    int resetPwd(Long userId);
}