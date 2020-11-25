package com.chenrj.zhihu.dao;

import com.chenrj.zhihu.model.Feed;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Feed record);

    int insertSelective(Feed record);

    Feed selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feed record);

    int updateByPrimaryKey(Feed record);

    List<Feed> selectUserFeeds(List<Integer> userIds, int maxId, int count);
}