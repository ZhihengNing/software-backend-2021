package com.yuki.experiment.common.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

//
//@Configuration
//// 配置mybatis的接口类放的地方
//@MapperScan(basePackages = "com.yuki.experiment.framework.mapper.mysql",
//        sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MysqlDataSourceConfig {


//    // 将这个对象放入Spring容器中
//    @Bean(name = "mysqlDataSource")
//    // 表示这个数据源是默认数据源
//    @Primary
//    // 读取application.properties中的配置参数映射成为一个对象
//    // prefix表示参数的前缀
//    @ConfigurationProperties(prefix = "spring.datasource.mysql")
//    public DataSource getDateSource1() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "mysqlSqlSessionFactory")
//    // 表示这个数据源是默认数据源
//    @Primary
//    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
//    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource datasource) throws Exception {
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//        bean.setDataSource(datasource);
//        bean.setMapperLocations(
//                // 设置mybatis的xml所在位置
//                new PathMatchingResourcePatternResolver()
//                        .getResources("classpath:mapper/**/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean("mysqlSqlSessionTemplate")
//    // 表示这个数据源是默认数据源
//    @Primary
//    public SqlSessionTemplate mysqlSqlSessionTemplate(
//            @Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sessionFactory) {
//        return new SqlSessionTemplate(sessionFactory);
//    }
}