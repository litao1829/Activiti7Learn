package com.litao.activiti;

import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AppDemo {
    public static void main(String[] args) {
        SpringApplication.run(AppDemo.class,args);
    }

    @Bean
    public UserGroupManager userGroupManager() {
        return new UserGroupManager() {
            @Override
            public List<String> getUserGroups(String s) {
                return null;
            }

            @Override
            public List<String> getUserRoles(String s) {
                return null;
            }

            @Override
            public List<String> getGroups() {
                return null;
            }

            @Override
            public List<String> getUsers() {
                return null;
            }
        };
    }

    @Bean
    public SecurityManager securityManager() {
        return new SecurityManager() {
            public String getAuthenticatedUserId() {
                return "miukoo-user-1";
            }

            public List<String> getAuthenticatedUserGroups() throws SecurityException {
                return null;
            }

            public List<String> getAuthenticatedUserRoles() throws SecurityException {
                return null;
            }
        };
    }

}
