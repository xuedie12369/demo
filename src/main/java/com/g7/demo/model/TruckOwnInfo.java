package com.g7.demo.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 
 * @Description: TODO(用一句话描述该文件做什么)
 * 
 * @author 魏晋隆
 * 
 * @date 2019年1月3日 下午1:58:38
 * 
 * @version V1.0
 **/
@Data
@ToString(includeFieldNames = true)
public class TruckOwnInfo implements Serializable {

	private static final long serialVersionUID = -4117724280995998288L;
	private String truckid;
	private long createtime;
	private String pownid;
	private int isshare;
	private String orgcode;
	private String channel_toorgcode;
	private String channel_fromorgcode;
	private String endtime;
	private String starttime;
	private String truck_ext_id;
	private String fromorgcode;
	private int deleted;
	private int fromtype;
	private String truckInfo;
	private String orgroot;
	private String id;
	private String updateuser;
	private String capabilities;
}
