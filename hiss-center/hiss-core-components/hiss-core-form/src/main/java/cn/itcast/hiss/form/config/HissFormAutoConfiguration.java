package cn.itcast.hiss.form.config;

import cn.itcast.hiss.form.mapper.HissFormCategoryMapper;
import cn.itcast.hiss.form.mapper.HissFormModelMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * HissFormAutoConfiguration
 *
 * @author: wgl
 * @describe: hiss表单自动配置类
 * @date: 2022/12/28 10:10
 */
@Configuration
@ComponentScan({"cn.itcast.hiss.form"})
public class HissFormAutoConfiguration {

    @Bean
    public MapperFactoryBean<HissFormModelMapper> hissFormModelMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<HissFormModelMapper> factoryBean = new MapperFactoryBean<>(HissFormModelMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<HissFormCategoryMapper> hissFormCategoryMapper(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<HissFormCategoryMapper> factoryBean = new MapperFactoryBean<>(HissFormCategoryMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
