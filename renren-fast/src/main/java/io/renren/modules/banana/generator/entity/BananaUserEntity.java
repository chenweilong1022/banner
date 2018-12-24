package io.renren.modules.banana.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@TableName("banana_user")
public class BananaUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId
	private Integer id;
	/**
	 * 用户手机号
	 */
	private String phone;
	/**
	 * 用户创建时间
	 */
	private Date createTime;
	/**
	 * 平台ios|安卓
	 */
	private String platform;

	/**
	 * 设置：用户id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：用户手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：用户手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：用户创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：用户创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：平台ios|安卓
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
	 * 获取：平台ios|安卓
	 */
	public String getPlatform() {
		return platform;
	}
}
