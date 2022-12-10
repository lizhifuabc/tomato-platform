package com.tomato.goods.skill.dao;

import com.tomato.goods.entity.SkillUserEntity;
import com.tomato.goods.skill.domain.bo.UpdateSkillRemainingBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户参与活动记录 {@link SkillUserEntity}
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Mapper
public interface SkillUserDao {
    /**
     * 更新秒杀剩余量
     * @return
     */
    public int updateSkillRemaining(UpdateSkillRemainingBO updateSkillRemainingBO);

    /**
     * 插入
     * @param skillUserEntity
     * @return
     */
    public void insert(SkillUserEntity skillUserEntity);
}
