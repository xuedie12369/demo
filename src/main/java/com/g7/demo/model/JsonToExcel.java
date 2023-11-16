package com.g7.demo.model;

import cn.hutool.core.collection.CollUtil;

/**
 * @author 邵海楠
 * @date 2023/11/13 15:29
 * @description
 */
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonToExcel {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> files = new ArrayList<>();
        String baseDir = "/Users/mac/g7e6/demo/src/main/resources/";
        String 量方平台产品 = baseDir + "量方平台产品" + ".json";
        String 冷链平台产品 = baseDir + "冷链平台产品" + ".json";
        String 载重平台产品 = baseDir + "载重平台产品" + ".json";
        String 车次中心 = baseDir + "车次中心" + ".json";
        String 追货 = baseDir + "追货" + ".json";
        //files.add(量方平台产品);
        //files.add(冷链平台产品);
        //files.add(车次中心);
        //files.add(载重平台产品);
        files.add(追货);
        // 创建Excel工作簿
        //Workbook workbook = new XSSFWorkbook();
        //Sheet sheet = workbook.createSheet("Applications");
        //Map<String, HashMap<String, String>> configs = JsonDataProcessor.getConfigs();


        try {
            for (String fielPath : files) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Applications");
                Map<String, HashMap<String, String>> configs = JsonDataProcessor.getConfigs();
                //Map<String, HashMap<String, String>> configs = JsonDataProcessor.getConfigs(JsonDataProcessor.parseJson("{\"spring.datasource.write.url\":\"jdbc:mysql://rm-bp1974ya0y45q9671.mysql.rds.aliyuncs.com:3306/intelli_trailer?useUnicode\\u003dtrue\\u0026characterEncoding\\u003dutf8\\u0026useSSL\\u003dfalse\\u0026allowMultiQueries\\u003dtrue\\u0026zeroDateTimeBehavior\\u003dconvertToNull\\u0026tinyInt1isBit\\u003dfalse\",\"spring.datasource.gto.password\":\"MHz1YJGmymhUG0Y0\",\"spring.redis.database\":\"2\",\"spring.redis.password\":\"KWboR9tNTS8nqG4Y\",\"spring.datasource.read.password\":\"guache_rw@20!(\",\"spring.datasource.read.url\":\"jdbc:mysql://rm-bp1974ya0y45q9671.mysql.rds.aliyuncs.com:3306/intelli_trailer?useUnicode\\u003dtrue\\u0026characterEncoding\\u003dutf8\\u0026useSSL\\u003dfalse\\u0026allowMultiQueries\\u003dtrue\\u0026zeroDateTimeBehavior\\u003dconvertToNull\\u0026tinyInt1isBit\\u003dfalse\",\"spring.redis.host\":\"r-bp1d92b5c065bfa4.redis.rds.aliyuncs.com\",\"spring.datasource.gto.username\":\"g7trailer_rw\",\"spring.datasource.amc.username\":\"g7amc_rw\",\"spring.datasource.amc.url\":\"jdbc:mysql://rm-bp1974ya0y45q9671.mysql.rds.aliyuncs.com:3306/g7amc?useUnicode\\u003dtrue\\u0026characterEncoding\\u003dutf8\\u0026useSSL\\u003dfalse\\u0026allowMultiQueries\\u003dtrue\\u0026zeroDateTimeBehavior\\u003dconvertToNull\\u0026tinyInt1isBit\\u003dfalse\",\"spring.redis.port\":\"6379\",\"spring.datasource.write.username\":\"g7amc_rw\",\"spring.datasource.amc.password\":\"YlME7vXTiao9lmS2\",\"spring.datasource.read.username\":\"guache_rw\",\"spring.datasource.gto.url\":\"jdbc:mysql://rm-bp1974ya0y45q9671.mysql.rds.aliyuncs.com:3306/g7trailer?useUnicode\\u003dtrue\\u0026characterEncoding\\u003dutf8\\u0026useSSL\\u003dfalse\\u0026allowMultiQueries\\u003dtrue\\u0026zeroDateTimeBehavior\\u003dconvertToNull\\u0026tinyInt1isBit\\u003dfalse\",\"spring.datasource.load.password\":\"B3JVaGFdNw90SNZp\",\"spring.datasource.write.password\":\"YlME7vXTiao9lmS2\",\"spring.datasource.load.url\":\"jdbc:mysql://rm-bp1spwv1148p7wx06.mysql.rds.aliyuncs.com:3306/basic_bearing?useUnicode\\u003dtrue\\u0026characterEncoding\\u003dutf8\\u0026useSSL\\u003dfalse\\u0026allowMultiQueries\\u003dtrue\\u0026zeroDateTimeBehavior\\u003dconvertToNull\\u0026tinyInt1isBit\\u003dfalse\",\"spring.datasource.load.username\":\"intellitrailer\"}\n"););

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
                headerRow.createCell(4).setCellValue("是否自动生成");

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
                    String code_repositories = application.get("code_repositories").asText();

                    row.createCell(2).setCellValue((code_repositories));
                    Map configMap = JsonDataProcessor.getConfigMap(name, configs);
                    if (CollUtil.isNotEmpty(configMap)) {
                        row.createCell(3).setCellValue(JSONUtil.formatJsonStr(configMap.toString()));
                        row.createCell(4).setCellValue("自动生成数据");

                    } else {
                        Map configMap2 = JsonDataProcessor.getConfigMap(replaceUnderscore(name), configs);
                        if (CollUtil.isNotEmpty(configMap2)) {
                            row.createCell(3).setCellValue(JSONUtil.formatJsonStr(configMap2.toString()));
                            row.createCell(4).setCellValue("自动生成数据");
                        }else{
                            name = extractRepoName(code_repositories);
                            Map configMap3 = JsonDataProcessor.getConfigMap(replaceUnderscore(name), configs);
                            if (CollUtil.isNotEmpty(configMap3)) {
                                row.createCell(3).setCellValue(JSONUtil.formatJsonStr(configMap3.toString()));
                                row.createCell(4).setCellValue("自动生成数据");
                            }else{
                                Map configMap4 = JsonDataProcessor.getConfigMap((name), configs);
                                if (CollUtil.isNotEmpty(configMap4)) {
                                    row.createCell(3).setCellValue(JSONUtil.formatJsonStr(configMap4.toString()));
                                    row.createCell(4).setCellValue("自动生成数据");
                                }
                            }
                        }
                    }
                }
                Row row = sheet.createRow(rowNum++);
                saveFile(workbook, getFileNameWithoutExtension(jsonFile) + ".xlsx");
            }
            // 自动打开文件所在目录
            // 生成保存文件的路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        //// 生成保存文件的路径
        //saveFile(workbook);
    }

    private static String replaceUnderscore(String input) {
        // 使用 replace 方法进行替换
        return input.replace('-', '_');
    }

    private static void saveFile(Workbook workbook, String fileName) throws IOException {
        //String outputPath = "applications.xlsx";

        // 将数据写入文件
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }

        // 关闭工作簿
        workbook.close();

        // 打印保存的文件路径
        File file1 = new File(fileName);
        System.out.println("输出文件: ");
        System.out.println(file1.getAbsolutePath());
    }


    private static String getFileNameWithoutExtension(File file) {


// 获取文件名称
        String fileName = file.getName();

        // 获取文件名不包含后缀的部分
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {


            return fileName.substring(0, lastDotIndex);
        } else {
            // 如果没有后缀，则返回整个文件名
            return fileName;
        }
    }

    public static String extractRepoName(String gitUrl) {
        // 使用最简单的方法，通过split和replace
        String[] parts = gitUrl.split("/");
        String lastPart = parts[parts.length - 1];
        String repoName = lastPart.replace(".git", "");
        return repoName;
    }

}

