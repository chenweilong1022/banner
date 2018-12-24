package io.renren.modules.banana.generator.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单表
 * 
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@TableName("banana_order")
public class BananaOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	@TableId
	private Integer orderid;
	/**
	 * 订单商品id
	 */
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
	 * 购买数量
	 */
	private BigDecimal count;
	/**
	 * 购买价钱
	 */
	private BigDecimal price;
	/**
	 * 商品总价格
	 */
	private BigDecimal totalPrice;
	/**
	 * 支付code
	 */
	private String code;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 支付时间
	 */
	private Date paytime;
	/**
	 * 支付方式1:微信2:支付宝
	 */
	private Integer payType;
	/**
	 * 充值账号
	 */
	private String account;
	/**
	 * 是否已经充值0:未充值 1:已经充值
	 */
	private Integer status;
	/**
	 * 该商品充值方式1:卡密2:自动充值3:手动充值
	 */
	private Integer topUpWay;
	/**
	 * 卡密
	 */
	private String password;
	/**
	 * 充值官方网站
	 */
	private String url;

	/**
	 * 设置：订单id
	 */
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	/**
	 * 获取：订单id
	 */
	public Integer getOrderid() {
		return orderid;
	}
	/**
	 * 设置：订单商品id
	 */
	public void setGoddsid(Integer goddsid) {
		this.goddsid = goddsid;
	}
	/**
	 * 获取：订单商品id
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
	 * 设置：购买数量
	 */
	public void setCount(BigDecimal count) {
		this.count = count;
	}
	/**
	 * 获取：购买数量
	 */
	public BigDecimal getCount() {
		return count;
	}
	/**
	 * 设置：购买价钱
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：购买价钱
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：商品总价格
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * 获取：商品总价格
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	/**
	 * 设置：支付code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：支付code
	 */
	public String getCode() {
		return code;
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
	 * 设置：支付时间
	 */
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}
	/**
	 * 获取：支付时间
	 */
	public Date getPaytime() {
		return paytime;
	}
	/**
	 * 设置：支付方式1:微信2:支付宝
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	/**
	 * 获取：支付方式1:微信2:支付宝
	 */
	public Integer getPayType() {
		return payType;
	}
	/**
	 * 设置：充值账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：充值账号
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：是否已经充值0:未充值 1:已经充值
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：是否已经充值0:未充值 1:已经充值
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：该商品充值方式1:卡密2:自动充值3:手动充值
	 */
	public void setTopUpWay(Integer topUpWay) {
		this.topUpWay = topUpWay;
	}
	/**
	 * 获取：该商品充值方式1:卡密2:自动充值3:手动充值
	 */
	public Integer getTopUpWay() {
		return topUpWay;
	}
	/**
	 * 设置：卡密
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：卡密
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：充值官方网站
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：充值官方网站
	 */
	public String getUrl() {
		return url;
	}
}
