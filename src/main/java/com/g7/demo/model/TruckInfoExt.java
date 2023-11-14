package com.g7.demo.model;

import lombok.Data;

import java.io.Serializable;

/**

* @Description: TODO(用一句话描述该文件做什么)

* @author 魏晋隆  

* @date 2019年1月3日 下午5:55:16

* @version V1.0 
**/
@Data
public class TruckInfoExt implements Serializable{

	private static final long serialVersionUID = -2946410713603494673L;
	private String carriagewidth;
	private String carriageheight;
	private String carriagelen;
}
