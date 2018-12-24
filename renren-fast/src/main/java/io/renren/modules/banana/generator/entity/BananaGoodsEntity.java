package io.renren.modules.banana.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品表
 * 
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@TableName("banana_goods")
public class BananaGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	private Integer goddsid;
	/**
	 * 商品名称
	 */
	private String title;
	/**
	 * 商品图片
	 */
	private String pic;
	/**
	 * 步骤富文本
	 */
	private unknowType steps;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 分享价格
	 */
	private BigDecimal share

sPrice;
	/**
	 * 充值方式1:卡密2:自动充值3:手动充值
	 */
	private Integer topUpWay;
	/**
	 * 卡密兑换地址--充值官网
	 */
	private String exchangeAddress;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 0:正常 -1:下架 -2:删除
	 */
	private Integer status;

	/**
	 * 设置：商品id
	 */
	public void setGoddsid(Integer goddsid) {
		this.goddsid = goddsid;
	}
	/**
	 * 获取：商品id
	 */
	public Integer getGoddsid() {
		return goddsid;
	}
	/**
	 * 设置：商品名称
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：商品名称
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：商品图片
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
	/**
	 * 获取：商品图片
	 */
	public String getPic() {
		return pic;
	}
	/**
	 * 设置：步骤富文本
	 */
	public void setSteps(unknowType steps) {
		this.steps = steps;
	}
	/**
	 * 获取：步骤富文本
	 */
	public unknowType getSteps() {
		return steps;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：分享价格
	 */
	public void setShare

sPrice(BigDecimal share

sPrice) {
		this.share

sPrice = share

sPrice;
	}
	/**
	 * 获取：分享价格
	 */
	public BigDecimal getShare

sPrice() {
		return share

sPrice;
	}
	/**
	 * 设置：充值方式1:卡密2:自动充值3:手动充值
	 */
	public void setTopUpWay(Integer topUpWay) {
		this.topUpWay = topUpWay;
	}
	/**
	 * 获取：充值方式1:卡密2:自动充值3:手动充值
	 */
	public Integer getTopUpWay() {
		return topUpWay;
	}
	/**
	 * 设置：卡密兑换地址--充值官网
	 */
	public void setExchangeAddress(String exchangeAddress) {
		this.exchangeAddress = exchangeAddress;
	}
	/**
	 * 获取：卡密兑换地址--充值官网
	 */
	public String getExchangeAddress() {
		return exchangeAddress;
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
	/**
	 * 设置：0:正常 -1:下架 -2:删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：0:正常 -1:下架 -2:删除
	 */
	public Integer getStatus() {
		return status;
	}
}
