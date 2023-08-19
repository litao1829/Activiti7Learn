package cn.itcast.hiss.server;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HissServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HissServerApplication.class,args);
    }

    @Autowired
    ProcessEngineConfigurationImpl processEngineConfiguration;
}
