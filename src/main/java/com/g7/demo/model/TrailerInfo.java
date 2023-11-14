package com.g7.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 挂车信息类
 *
 * @author 魏晋隆
 * @version V1.0
 * 2019年1月2日 上午10:21:59
 **/

@Data
public class TrailerInfo implements Serializable{

    private static final long serialVersionUID = 1066805594708676676L;
    private String id;
    private String carnum;
    private int gpsno;
    private String orgRoot;//顶级组织机构代码，为自有组织结构代码
    private String owner;
    private TruckOwnInfo truckOwn;

    private int isAuth;
    private int isHeadStock;//2挂车
    private double volume;//额定体积
    private double weight;//额定载重
    private TruckInfoExt truckInfoExt;

    private String orgCode;
    private List<String> innershare;//内部共享机构号
    private List<String> outshare;//外部共享机构号

    private String matchtruckid;//匹配车头编号
    private String matchcarnum;//匹配车头车牌号

}
