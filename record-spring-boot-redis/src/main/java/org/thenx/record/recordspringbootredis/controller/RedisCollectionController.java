package org.thenx.record.recordspringbootredis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thenx.record.recordspringbootredis.service.RedisService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author May
 * <p>
 * Redis 集合操作
 */
@RestController
public class RedisCollectionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis list操作
     *
     * @param i id
     * @return str
     */
    @GetMapping(value = "/list")
    public String redis(@RequestParam("id") Integer i) {

        List<String> list = new ArrayList<>();

        String id;
        if (i == null) {
            throw new RuntimeException("参数没有传对: " + i);
        } else {
            id = i + "";
        }

        RedisService redisService = null;
        // Redis list 操作
        redisService = p -> {
            list.add("第一个: " + p);
            list.add("第二个: " + p + 1);
            list.add("第三个: " + p + 2);
            redisTemplate.opsForList().leftPush(p, list);
            logger.info("\n----------> list: " + redisTemplate.opsForList().leftPop(p));
            return "list 中的参数：" + redisTemplate.opsForList().leftPop(p);
        };
        return redisService.resp(id);
    }

    /**
     * Redis map操作
     *
     * @param i id
     * @return str
     */
    @GetMapping(value = "/map")
    public String redisMap(@RequestParam("id") Integer i) {

        Map<String, Object> map = new HashMap<>(8);

        String id;
        if (i == null) {
            throw new RuntimeException("参数没有传对: " + i);
        } else {
            id = i + "";
        }

        RedisService redisService = null;
        // Redis map 操作
        redisService = p -> {
            map.put("第一个 map: ", "map1");
            map.put("第二个 map: ", "map2");
            map.put("第三个 map: ", "map3");
            redisTemplate.opsForHash().putAll(p, map);
            logger.info("\n----------> map中的值: " + redisTemplate.opsForHash().values(p));
            logger.info("\n----------> map中的键值对: " + redisTemplate.opsForHash().entries(p));
            return "list 中的参数：" + redisTemplate.opsForHash().values(p);
        };
        return redisService.resp(id);
    }
}
