package com.jifenchaxundaoru.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 积分列表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-26 18:42:54
 */
public class IntegralListDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//序号
	private String sort;
	//身份证号
	private String identityCard;
	//姓名
	private String uname;
	//培训名称
	private String trainName;
	//课程名称
	private String courseName;
	//课程类型
	private String courseType;
	//学习状态
	private String learnState;
	//课程成绩
	private String courseResult;
	//开始学习时间
	private Date startLearnTime;
	//最后学习时间
	private Date endLearnTime;
	//获得积分
	private String winIntegral;
	//1:正常2:禁止
	private Integer status;
	private String totalIntegral;
	
	
	
	public String getTotalIntegral() {
		return totalIntegral;
	}
	public void setTotalIntegral(String totalIntegral) {
		this.totalIntegral = totalIntegral;
	}
	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：序号
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * 获取：序号
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * 设置：身份证号
	 */
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	/**
	 * 获取：身份证号
	 */
	public String getIdentityCard() {
		return identityCard;
	}
	/**
	 * 设置：姓名
	 */
	public void setUname(String uname) {
		this.uname = uname;
	}
	/**
	 * 获取：姓名
	 */
	public String getUname() {
		return uname;
	}
	/**
	 * 设置：培训名称
	 */
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	/**
	 * 获取：培训名称
	 */
	public String getTrainName() {
		return trainName;
	}
	/**
	 * 设置：课程名称
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * 获取：课程名称
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * 设置：课程类型
	 */
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	/**
	 * 获取：课程类型
	 */
	public String getCourseType() {
		return courseType;
	}
	/**
	 * 设置：学习状态
	 */
	public void setLearnState(String learnState) {
		this.learnState = learnState;
	}
	/**
	 * 获取：学习状态
	 */
	public String getLearnState() {
		return learnState;
	}
	/**
	 * 设置：课程成绩
	 */
	public void setCourseResult(String courseResult) {
		this.courseResult = courseResult;
	}
	/**
	 * 获取：课程成绩
	 */
	public String getCourseResult() {
		return courseResult;
	}
	/**
	 * 设置：开始学习时间
	 */
	public void setStartLearnTime(Date startLearnTime) {
		this.startLearnTime = startLearnTime;
	}
	/**
	 * 获取：开始学习时间
	 */
	public Date getStartLearnTime() {
		return startLearnTime;
	}
	/**
	 * 设置：最后学习时间
	 */
	public void setEndLearnTime(Date endLearnTime) {
		this.endLearnTime = endLearnTime;
	}
	/**
	 * 获取：最后学习时间
	 */
	public Date getEndLearnTime() {
		return endLearnTime;
	}
	/**
	 * 设置：获得积分
	 */
	public void setWinIntegral(String winIntegral) {
		this.winIntegral = winIntegral;
	}
	/**
	 * 获取：获得积分
	 */
	public String getWinIntegral() {
		return winIntegral;
	}
	/**
	 * 设置：1:正常2:禁止
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：1:正常2:禁止
	 */
	public Integer getStatus() {
		return status;
	}
}
