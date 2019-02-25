package io.renren.modules.banana.generator.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品卡密
 * 
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@TableName("banana_goods_camilo")
public class BananaGoodsCamiloEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 卡密id
	 */
	@TableId
	private Integer id;
	/**
	 * 商品id
	 */
	private Integer goodsId;
	/**
	 * 卡密
	 */
	private String camilo;
	/**
	 * 卡密 \r\n分割
	 */
	@TableField(exist = false)
	private String textarea;
	/**
	 * 状态0:未发放1:已发放
	 */
	private Integer status;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：卡密id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：卡密id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：商品id
	 */
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * 获取：商品id
	 */
	public Integer getGoodsId() {
		return goodsId;
	}
	/**
	 * 设置：卡密
	 */
	public void setCamilo(String camilo) {
		this.camilo = camilo;
	}
	/**
	 * 获取：卡密
	 */
	public String getCamilo() {
		return camilo;
	}
	/**
	 * 设置：状态0:未发放1:已发放
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态0:未发放1:已发放
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：商品名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public String getTextarea() {
		return textarea;
	}

	public void setTextarea(String textarea) {
		this.textarea = textarea;
	}
}
