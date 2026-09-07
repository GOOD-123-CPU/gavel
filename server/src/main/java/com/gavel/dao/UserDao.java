
package com.gavel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gavel.entity.UserEntity;

/**
 * 用户
 */
public interface UserDao extends BaseMapper<UserEntity> {
	
	List<UserEntity> selectListView(@Param("ew") Wrapper<UserEntity> wrapper);

	List<UserEntity> selectListView(IPage page, @Param("ew") Wrapper<UserEntity> wrapper);
	
}
