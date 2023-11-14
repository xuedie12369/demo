package com.g7.demo.model;


public class ConfigExtractor {

    public static void main(String[] args) {
        String jsonString = "{\n" +
                "    \"application\": {\n" +
                "    },\n" +
                "    \"Technology_platform_Intelli_trailer.intelli_trailer\": {\n" +
                "        \"spring.redis.database\": \"2\",\n" +
                "        \"spring.redis.password\": \"K1231234Y\",\n" +
                "        \"spring.datasource.read.password\": \"guache_rw@20!(\",\n" +
                "        \"spring.datasource.trailer.jdbcUrl\": \"jdbc:mysql://cs.com:3306/intelli_trailer?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true\",\n" +
                "        \"spring.redis.host\": \"r-bom\",\n" +
                "        \"spring.redis.port\": \"6379\",\n" +
                "        \"spring.datasource.write.username\": \"gadasdasdasdasddasdasdche_rw\",\n" +
                "        \"spring.datasource.read.jdbcUrl\": \"jdbc:mysql://asdasdasdm:3306/intelli_trailer?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true\",\n" +
                "        \"spring.datasource.read.username\": \"guacxxxxxaase123axzcxgsdfgasd_rw\",\n" +
                "        \"spring.data.mongodb.port\": \"3722217\",\n" +
                "        \"spring.data.mongodb.host\": \"dds-asdasdcom\",\n" +
                "        \"weijinlong\": \"kwQxXIrq0Av72ymV\",\n" +
                "        \"spring.datasource.write.jdbcUrl\": \"jdbc:mysql://rm-basdasd:3306/intelli_trailer?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true\",\n" +
                "        \"spring.data.mongodb.username\": \"coldca12sdfsdfxbvsg234sd123wr\",\n" +
                "        \"spring.datasource.write.password\": \"guachasdasdasd\",\n" +
                "        \"spring.datasource.trailer.username\": \"guacasdrw\",\n" +
                "        \"spring.data.mongodb.database\": \"intasdadailer\",\n" +
                "        \"spring.data.mongodb.password\": \"cCSHasdadsQHN\",\n" +
                "        \"spring.datasource.trailer.password\": \"ssssuacasd!(\"\n" +
                "    },\n" +
                "    \"intelli_truck\": {\n" +
                "        \"weijinlong\": \"kwQxXasdasdmV\"\n" +
                "    }\n" +
                "}";

        // 手动解析 JSON 字符串
        extractConfig(jsonString);
    }

    private static void extractConfig(String jsonString) {
        String[] entries = jsonString.split("\"|\\{|\\}");

        for (String entry : entries) {
            entry = entry.trim();
            if (entry.startsWith(":") && entry.contains("spring.redis")) {
                System.out.println(entry);
            }
        }
    }
}
