package com.neo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

@ApiModel
public class Message {
	private Long id;
	@ApiModelProperty(value = "消息体")
	private String text;
	@ApiModelProperty(value = "消息总结")
	private String summary;
	@ApiModelProperty(value = "时间戳",dataType = "java.lang.Long")
	private Timestamp timestamp;
	@ApiModelProperty(value = "标记")
	private boolean flag;
	private Date createDate;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", text='" + text + '\'' +
				", summary='" + summary + '\'' +
				", createDate=" + createDate +
				'}';
	}
}
