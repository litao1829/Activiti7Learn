package cn.itcast.hiss.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.itcast.hiss.server.mapper")
public class MapperConfig {
}
