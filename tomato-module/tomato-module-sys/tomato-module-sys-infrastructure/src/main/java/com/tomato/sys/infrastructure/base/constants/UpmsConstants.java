package com.tomato.sys.infrastructure.base.constants;

/**
 * <p>Description: Upms 模块常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/16 18:19
 */
public interface UpmsConstants {

    String AREA_PREFIX = "data:upms:";

    String REGION_SYS_USER = AREA_PREFIX + "sys:user";
    String REGION_SYS_ROLE = AREA_PREFIX + "sys:role";
    String REGION_SYS_DEFAULT_ROLE = AREA_PREFIX + "sys:defaults:role";
    String REGION_SYS_AUTHORITY = AREA_PREFIX + "sys:authority";
    String REGION_SYS_INTERFACE = AREA_PREFIX + "sys:interface";
    String REGION_SYS_PERMISSION = AREA_PREFIX + "sys:permission";
    String REGION_SYS_SCOPE = AREA_PREFIX + "sys:scope";
    String REGION_SYS_ATTRIBUTE = AREA_PREFIX + "sys:attribute";
    String REGION_SYS_OWNERSHIP = AREA_PREFIX + "sys:ownership";
    String REGION_SYS_ELEMENT = AREA_PREFIX + "sys:element";
    String REGION_SYS_SOCIAL_USER = AREA_PREFIX + "sys:social:user";
    String REGION_SYS_CLIENT = AREA_PREFIX + "sys:client";

    String REGION_SYS_DEPARTMENT = AREA_PREFIX + "sys:department";
    String REGION_SYS_EMPLOYEE = AREA_PREFIX + "sys:employee";
    String REGION_SYS_EMPLOYEE_DEPARTMENT = AREA_PREFIX + "sys:employee:department";
    String REGION_SYS_ORGANIZATION = AREA_PREFIX + "sys:organization";
    String REGION_SYS_POSITION = AREA_PREFIX + "sys:position";
}
