package com.tuotiansudai.worker.monitor;

import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Clock;
import java.util.Map;
import java.util.Properties;

public class MQRunningStatusCheck {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            return;
        }
        String configFile = args[0];
        Properties properties = new Properties();
        properties.load(new FileInputStream(configFile));

        String redisHost = properties.getProperty("common.redis.host");
        int redisPort = Integer.parseInt(properties.getProperty("common.redis.port"));
        String redisPassword = properties.getProperty("common.redis.password");
        int redisDB = Integer.parseInt(properties.getProperty("common.redis.db"));

        Jedis jedis = new Jedis(redisHost, redisPort);
        if (!StringUtils.isEmpty(redisPassword)) {
            jedis.auth(redisPassword);
        }
        jedis.connect();
        jedis.select(redisDB);

        Map<String, String> workerMap = jedis.hgetAll(WorkerMonitor.HEALTH_REPORT_REDIS_KEY);
        System.out.println(String.format("worker 状态检查, 共发现 %d 个Worker有运行记录：", workerMap.size()));
        long nowMills = Clock.systemUTC().millis();
        long missingWorkerCount = workerMap.entrySet().stream()
                .filter(entry -> isMissingNow(entry.getKey(), entry.getValue(), nowMills))
                .count();
        jedis.close();

        if (missingWorkerCount > 0) {
            System.exit(1);
        }
    }

    private static boolean isMissingNow(String worker, String reportTime, long nowMills) {
        long lastReportTime = 0;
        try {
            lastReportTime = Long.parseLong(reportTime);
        } catch (Exception ignored) {
        }
        if (lastReportTime > 0) {
            long passedSeconds = ((nowMills - lastReportTime) / 1000);
            if (passedSeconds < 20) {
                System.out.println(String.format("%50s : %s 秒前更新了状态", worker, passedSeconds));
                return false;
            } else {
                System.out.println(String.format("%50s : %s 秒前更新了状态，可能出现异常", worker, passedSeconds));
                return true;
            }
        } else {
            System.out.println(String.format("%50s : 状态异常", worker));
            return true;
        }
    }
}
