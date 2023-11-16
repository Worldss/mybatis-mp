<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${entityInfo.mapperPackage}.${entityInfo.mapperName}">

<#if mapperXmlConfig.isResultMap()>
    <!-- 通用查询映射结果 -->
    <resultMap id="ResultMap" type="${entityInfo.entityPackage}.${entityInfo.name}">
    <#list entityInfo.allFiledInfoList() as field>
    <#if field.columnInfo.primaryKey>
        <id column="${field.columnInfo.name}" property="${field.name}" />
    <#else>
        <result column="${field.columnInfo.name}" property="${field.name}" />
    </#if>
    </#list>
    </resultMap>
</#if>

<#if mapperXmlConfig.isColumnList()>
    <!-- 通用查询结果列 -->
    <sql id="ColumnList">

<#list entityInfo.allFiledInfoList() as field>
    <#if field_index==0>
        ${field.columnInfo.name}
    <#else>
        ,${field.columnInfo.name}
    </#if>
</#list>
    </sql>
</#if>
</mapper>
