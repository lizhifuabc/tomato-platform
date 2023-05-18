package com.tomato.mybatis.mapper;

/**
 * ExampleXML
 *
 * @author lizhifu
 * @since 2023/5/17
 */
public class ExampleXML {
    public static void main(String[] args) {
        String className = "NoticeRuleEntity";
        StringBuilder builder = new StringBuilder();
        builder.append("<where>");
        builder.append("<foreach collection='oredCriteria' item='criteria'>");
        builder.append("<if test='criteria.valid'>");
        builder.append(" ${criteria.andOr} ");
        builder.append("<trim prefix='(' prefixOverrides='AND|OR' suffix=')'>");
        builder.append("<foreach collection='criteria.criteria' item='criterion'>");
        builder.append("<choose>");
        builder.append("<when test='criterion.noValue'>");
        builder.append(String.format(" ${criterion.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',criterion.property)} ${criterion.condition}", className));
        builder.append("</when>");
        builder.append("<when test='criterion.singleValue'>");
        builder.append(String.format(" ${criterion.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',criterion.property)} ${criterion.condition} #{criterion.value}", className));
        builder.append("</when>");
        builder.append("<when test='criterion.betweenValue'>");
        builder.append(String.format(" ${criterion.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',criterion.property)} ${criterion.condition} #{criterion.value} AND #{criterion.secondValue}", className));
        builder.append("</when>");
        builder.append("<when test='criterion.listValue'>");
        builder.append(String.format(" ${criterion.andOr} ${@tech.wetech.mybatis.util.EntityMappingUtil@getColumnName('%s',criterion.property)} ${criterion.condition} ", className));
        builder.append("<foreach collection='criterion.value' item='id' index='index' open='(' close=')' separator=', '>#{id}</foreach>");
        builder.append("</when>");
        builder.append("</choose>");
        builder.append("</foreach>");
        builder.append("</trim>");
        builder.append("</if>");
        builder.append("</foreach>");
        builder.append("</where>");
        System.out.println(builder.toString());
    }
}
