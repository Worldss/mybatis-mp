package com.test.DO;

import java.time.LocalTime;
import cn.mybatis.mp.db.annotations.TableId;
import cn.mybatis.mp.db.annotations.TableField;
import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.IdAutoType;

/**
 * <p>
 * 微信用户
 * </p>
 *
 * @author 
 * @since 2023-11-16
 */

@Table(value="wx_user")
public class AppUser {

    /**
     * ID
     */
    @TableId(IdAutoType.AUTO)
    private Long id;

    /**
     * UnionID
     */
    private String unionId;

    /**
     * openid
     */
    private String openId;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 会话KEY
     */
    private String sessionKey;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 创建时间
     */
    @TableField(update = false)
    private LocalTime createTime;

    public void setId (Long id) {
        this.id = id;
    }

    public Long getId () {
        return this.id;
    }

    public void setUnionId (String unionId) {
        this.unionId = unionId;
    }

    public String getUnionId () {
        return this.unionId;
    }

    public void setOpenId (String openId) {
        this.openId = openId;
    }

    public String getOpenId () {
        return this.openId;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public String getPhone () {
        return this.phone;
    }

    public void setSessionKey (String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionKey () {
        return this.sessionKey;
    }

    public void setNickName (String nickName) {
        this.nickName = nickName;
    }

    public String getNickName () {
        return this.nickName;
    }

    public void setAvatarUrl (String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl () {
        return this.avatarUrl;
    }

    public void setCreateTime (LocalTime createTime) {
        this.createTime = createTime;
    }

    public LocalTime getCreateTime () {
        return this.createTime;
    }

    @Override
    public String toString() {
        return "AppUser{" +
            "id = " + id +
            ", unionId = " + unionId +
            ", openId = " + openId +
            ", phone = " + phone +
            ", sessionKey = " + sessionKey +
            ", nickName = " + nickName +
            ", avatarUrl = " + avatarUrl +
            ", createTime = " + createTime +
        "}";
    }
}
