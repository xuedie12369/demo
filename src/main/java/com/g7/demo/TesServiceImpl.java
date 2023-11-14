package com.g7.demo;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

/**
 * @author 邵海楠
 * @date 2023/10/27 17:18
 * @description
 */
@Service
public class TesServiceImpl implements DisposableBean {


    @Override
    public void destroy() throws Exception {
        System.out.println("开始销毁bean了");
    }
}
