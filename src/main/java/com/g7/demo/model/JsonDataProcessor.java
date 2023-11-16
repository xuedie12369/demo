package com.g7.demo.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class JsonDataProcessor {


    public static Map getConfigMap(String name, Map<String, HashMap<String, String>> configs) {
        //HashMap<String, HashMap<String, String>> configs = getConfigs();
        HashMap<String, String> coldchain_biz_server = configs.get(name);
        if (CollUtil.isEmpty(coldchain_biz_server)) {
            System.out.println("查询数据为空" + name);
            return null;
        }
        //writer.append("AppId,Redis,MySQL,Mongo,HBase,DRDS,RocketMQ,Kafka,OSS,ZooKeeper\n");
        //Map<String, String> stringStringMap = filterMapByPrefix(coldchain_biz_server, "spring.redis");
//| redis | mysql | mongo | hbase
// 未处理的  // | drds| RocketMQ | kafka | 域名 |
// OSS信息 | zk信息|等
        Map result = fileterMap(name, coldchain_biz_server);
        return result;

    }

    static Map fileterMap(String name, HashMap<String, String> coldchain_biz_server) {
        Map result = new HashMap<String, String>();
        Map redisMap = getRedisMap(coldchain_biz_server);
        Map<String, String> redis = filterMapByRegex(coldchain_biz_server,
                ".*\\.redis\\..*"
                ,"redis\\..*");
        //Map<String, String> redis3 = filterMapByRegex(coldchain_biz_server, "spring\\.redis\\..*");
        result.putAll(redis);
        //result.putAll(redis3);
        result.putAll(redisMap);
        //mysql的
        Map<String, String> mysql = filterMapByRegex(coldchain_biz_server, "spring\\.datasource\\..*\\.read\\.jdbcUrl");
        Map<String, String> rds = filterMapByRegex(coldchain_biz_server, "spring\\.datasource\\..*\\.rds\\..*");
        Map<String, String> oss = filterMapByRegex(coldchain_biz_server,
                "oss\\..*"
                ,".*oss.*"
                ,".*Oss.*"
        );
        Map<String, String> MongoDB = filterMapByRegex(coldchain_biz_server, "spring\\.data\\.mongodb\\.*");
        Map<String, String> zookeeper = filterMapByRegex(
                coldchain_biz_server,
                "zookeeper\\..*",
                "zookeeper.address.host",
                "primary.hbase.zookeeper.quorum",
                ".*\\.zookeeper\\..*"
                );
        Map<String, String> hbase = filterMapByRegex(coldchain_biz_server, "hbase\\..*");
        Map<String, String> kafka = filterMapByRegex(coldchain_biz_server, "kafka\\.consumer\\.servers\\.*");
        Map<String, String> kafka2 = filterMapByRegex(
                coldchain_biz_server, "kafka\\.broker\\..*");
        Map<String, String> kafka3 = filterMapByRegex(coldchain_biz_server,
                "kafka\\.producer\\.topic\\.*",
                "kafka\\.\\w+\\.consumer\\.group\\.id",
                "kafka\\.producer\\.bootstrap-servers");
        Map<String, String> rocketmq = filterMapByRegex(coldchain_biz_server, "rocketmq\\.consumer\\..*");
        Map<String, String> db = filterMapByRegex(coldchain_biz_server, "spring\\.datasource\\..*\\.url");
        Map<String, String> mq = filterMapByRegex(coldchain_biz_server, "mq.nameserver");
        Map<String, String> mysqlUrl = filterMapByRegex(coldchain_biz_server, "mysql.url");
        Map<String, String> kafkaf = filterMapByRegex(coldchain_biz_server, "kafka.loadEvent.topic");
        Map<String, String> brokers = filterMapByRegex(coldchain_biz_server,
                "kafka.brokers",
                "kafka.servers",
                "kafka.producer.smooth.topic",
                "kafka.consumer.\\..*\\.topic"
                ,"bucketName"
                ,"kafka.kdc"
        );
        Map<String, String> group = filterMapByRegex(coldchain_biz_server, "kafka.loadEvent.group.id");
        result.putAll(mysqlUrl);
        result.putAll(kafkaf);
        result.putAll(brokers);
        result.putAll(group);

        //Map<String, String> hbase = filterMapByRegex(coldchain_biz_server, "hbase.\\..*");
        if ("load-ird-partner-algorithm".equals(name)) {
            System.out.println("asdasd");
        }
        result.putAll(db);
        result.putAll(mq);
        result.putAll(rocketmq);
        result.putAll(mysql);
        result.putAll(rds);
        //result.putAll(mysql);
        result.putAll(kafka);
        result.putAll(kafka2);
        result.putAll(kafka3);
        result.putAll(oss);
        result.putAll(MongoDB);
        result.putAll(zookeeper);
        result.putAll(hbase);


        result.remove("hbase.client.username");
        result.remove("hbase.client.username");
        result.remove("spring.redis.password");
        result.remove("spring.redis.area.lettuce.pool.min-idle");
        result.remove("spring.redis.area.lettuce.pool.max-wait");
        filterMap(result, "spring\\.datasource\\..*\\.driverClassName");
        filterMap(result, "spring\\.datasource\\..*\\.password");
        filterMap(result, "spring\\.datasource\\..*\\.username");
        filterMap(result, "spring\\.redis\\..*\\.username");
        filterMap(result, "spring\\.redis\\..*\\.password");
        filterMap(result, "spring\\.redis\\..*\\.time");
        filterMap(result, "aspect.distribution.redis.lock.enable");
        filterMap(result, "spring\\.redis\\..*\\.shutdown-timeout");
        filterMap(result, "spring\\.redis\\..*\\.max-wait");
        return result;
    }

    private static void filterMap(Map<String, String> map, String regex) {
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (  Pattern.compile(regex).matcher(entry.getKey()).matches()) {
                iterator.remove(); // 删除符合条件的键值对
            }
        }
    }
    private static Map<String, String> filterMapByRegex(Map<String, String> originalMap, String ...regexs) {
        Map<String, String> filteredMap = new LinkedHashMap<>();
        for (String regex : regexs) {

            Pattern pattern = Pattern.compile(regex);
            for (Map.Entry<String, String> entry : originalMap.entrySet()) {
                String key = entry.getKey();
                if (pattern.matcher(key).matches()) {
                    filteredMap.put(key, entry.getValue());
                }
            }
        }
        return filteredMap;
    }

    public static Map getRedisMap(Map<String, String> stringStringMap) {
        Map<Object, Object> objectObjectHashMap = new LinkedHashMap<>();
        String database = "spring.redis.database";
        String host = "spring.redis.host";
        String port = "spring.redis.port";
        if (StrUtil.isNotBlank(stringStringMap.get(database))) {
            objectObjectHashMap.put(database, stringStringMap.get(database));
        }
        if (StrUtil.isNotBlank(stringStringMap.get(host))) {
            objectObjectHashMap.put(host, stringStringMap.get(host));
        }
        if (StrUtil.isNotBlank(stringStringMap.get(port))) {
            objectObjectHashMap.put(port, stringStringMap.get(port));
        }

        return objectObjectHashMap;
    }

    private static Map<String, String> filterMapByPrefix(Map<String, String> originalMap, String prefix) {
        Map<String, String> filteredMap = new HashMap<>();
        for (Map.Entry<String, String> entry : originalMap.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(prefix)) {
                filteredMap.put(key, entry.getValue());
            }
        }
        return filteredMap;
    }

    public static Map<String, HashMap<String, String>> getConfigs() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 从文件中读取JSON字符串
            Path filePath = Paths.get("/Users/mac/g7e6/coldchain_biz_server/src/main/java/com/chinaway/intelli_trailer/model/d.json");
            String json = new String(Files.readAllBytes(filePath));

            // 将JSON字符串转换为Java对象
            Map<String, Map<String, JsonNode>> groupedData = parseJson(json);

            // 提取信息并写入CSV文件
            //writeCsv(groupedData, "/Users/mac/Desktop/c.csv");
            //得到的是appid和配置项的值
            Map<String, HashMap<String, String>> configs = getConfigs(groupedData);
            return configs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Map<String, HashMap<String, String>> getConfigs(Map<String, Map<String, JsonNode>> groupedData) {
        Map<String, HashMap<String, String>> result = new TreeMap<>();
        groupedData.forEach((k, v) -> {
            HashMap<String, String> objectObjectHashMap = new HashMap<>();
            v.forEach((k1, v1) -> {
                extractKeyValue(v1, objectObjectHashMap);
            });
            result.put(k, objectObjectHashMap);
        });
        return result;
    }

    private static void extractKeyValue(JsonNode jsonNode, Map<String, String> resultMap) {
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();

            if (value.isObject()) {
                // 如果值是对象，递归处理
                extractKeyValue(value, resultMap);
            } else {
                // 否则将键值对添加到 resultMap 中
                resultMap.put(key, value.asText());
            }
        }
    }

    public static Map<String, Map<String, JsonNode>> parseJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<List<String>> rows = objectMapper.readValue(json, List.class);

        // 使用 Map 进行分组和合并
        Map<String, Map<String, JsonNode>> groupedData = new HashMap<>();
        for (List<String> row : rows) {
            String appId = row.get(0);
            String namespace = row.get(1);
            String configurations = row.get(2);

            // 创建唯一标识符
            String key = appId;

            // 将JSON字符串解析为 JsonNode
            JsonNode configNode = objectMapper.readTree(configurations);

            // 将数据合并到 Map 中
            groupedData.computeIfAbsent(key, k -> new HashMap<>());
            groupedData.get(key).put(namespace, configNode);
        }

        return groupedData;
    }

    private static void writeCsv(Map<String, Map<String, JsonNode>> groupedData, String outputCsvPath) {
        try (FileWriter writer = new FileWriter(outputCsvPath)) {
            // 写入CSV文件头部
            writer.append("AppId,Redis,MySQL,Mongo,HBase,DRDS,RocketMQ,Kafka,OSS,ZooKeeper\n");

            // 遍历每个AppId的配置
            for (Map.Entry<String, Map<String, JsonNode>> entry : groupedData.entrySet()) {
                String appId = entry.getKey();
                Map<String, JsonNode> namespaceConfigMap = entry.getValue();
                // 写入AppId
                writer.append(appId);

                // 提取并写入指定配置信息
                writer.append(",");
                writeField(writer, namespaceConfigMap, "redis");
                writeField(writer, namespaceConfigMap, "mysql");
                writeField(writer, namespaceConfigMap, "mongo");
                writeField(writer, namespaceConfigMap, "hbase");
                writeField(writer, namespaceConfigMap, "drds");
                writeField(writer, namespaceConfigMap, "RocketMQ");
                writeField(writer, namespaceConfigMap, "kafka");
                writeField(writer, namespaceConfigMap, "OSS");
                writeField(writer, namespaceConfigMap, "zk");

                writer.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从指定配置项提取信息并写入CSV文件
    private static void writeField(FileWriter writer, Map<String, JsonNode> namespaceConfigMap, String field) throws IOException {
        JsonNode configNode = namespaceConfigMap.get(field);

        if (configNode != null && configNode.has(field)) {
            writer.append(configNode.get(field).toString());
        }
    }
}
