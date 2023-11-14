package com.g7.demo.model;

import cn.hutool.core.collection.CollUtil;

/**
 * @author 邵海楠
 * @date 2023/11/13 15:29
 * @description
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonToExcel {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> files = new ArrayList<>();
        String baseDir="/Users/mac/g7e6/coldchain_biz_server/src/main/java/com/chinaway/intelli_trailer/model/";
        String 量方平台产品 = baseDir+"量方平台产品"+".json";
        String 冷链平台产品 = baseDir+"冷链平台产品"+".json";
        String 载重平台产品 = baseDir+"载重平台产品"+".json";
        //files.add(量方平台产品);
        files.add(冷链平台产品);
        files.add(载重平台产品);
        // 创建Excel工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Applications");
        Map<String, HashMap<String, String>> configs = JsonDataProcessor.getConfigs();



        try {
            for (String fielPath : files) {

                String baseServiceURl = "https://devops.chinawayltd.com/system/deliver/application/detail?id=";
                // 从外部文件读取JSON数据

                File jsonFile = new File(fielPath);
                JsonNode jsonNode = objectMapper.readTree(jsonFile);

                // 创建表头
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("服务名");
                headerRow.createCell(1).setCellValue("devops地址");
                headerRow.createCell(2).setCellValue("git地址");
                headerRow.createCell(3).setCellValue("资源依赖项");

                // 填充数据
                int rowNum = 1;
                for (JsonNode application : jsonNode) {
                    Row row = sheet.createRow(rowNum++);
                    //服务名称
                    String label = application.get("label").asText();
                    String name = application.get("name").asText();

                    row.createCell(0).setCellValue(application.get("label").asText() + "（" + application.get("name").asText() + "）");
                    //develops地址
                    row.createCell(1).setCellValue(baseServiceURl + application.get("ID").asText());
                    //row.createCell(1).setCellValue(application.get("name").asText());
                    //git地址
                    row.createCell(2).setCellValue(application.get("code_repositories").asText());
                    Map configMap = JsonDataProcessor.getConfigMap(name,configs);
                    if (CollUtil.isNotEmpty(configMap)){
                        row.createCell(3).setCellValue(configMap.toString());
                    }else{
                        Map configMap2= JsonDataProcessor.getConfigMap(  replaceUnderscore(name),configs);
                        if (CollUtil.isNotEmpty(configMap2)){
                            row.createCell(3).setCellValue(configMap2.toString());
                        }
                    }
                }
                Row row = sheet.createRow(rowNum++);
            }
            // 自动打开文件所在目录
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 生成保存文件的路径
        saveFile(workbook);
    }
    private static String replaceUnderscore(String input) {
        // 使用 replace 方法进行替换
        return input.replace( '-','_');
    }
    private static void saveFile(Workbook workbook) throws IOException {
        String outputPath = "applications.xlsx";

        // 将数据写入文件
        try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
            workbook.write(fileOut);
        }

        // 关闭工作簿
        workbook.close();

        // 打印保存的文件路径
        File file1 = new File(outputPath);

        System.out.println("输出文件: " );
        System.out.println(file1.getAbsolutePath() );
    }
}

