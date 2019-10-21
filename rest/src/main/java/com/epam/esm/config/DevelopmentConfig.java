package com.epam.esm.config;

import com.epam.esm.mapperinterface.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class we use to configuration, scan and generate transaction management
 * classes for spring. And we use for it 5 annotations.
 * PropertySource we use for read configuration for create connection with database
 * to working all methods in this application on web side.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Profile("dev")
@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class DevelopmentConfig {

    /**
     * Element to create connection.
     */
    @Value("${repository.db.url}")
    private String dbUrl;
    /**
     * Element to create connection.
     */
    @Value("${repository.db.login}")
    private String dbLogin;
    /**
     * Element to create connection.
     */
    @Value("${repository.db.password}")
    private String dbPassword;

    /**
     * Element to create connection.
     */
    @Value("${repository.db.driver}")
    private String dbDriver;

    /**
     * Constructor without parameters.
     */
    public DevelopmentConfig() {
    }

    /**
     * @return hikariConfig.
     */
    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setPassword(dbPassword);
        hikariConfig.setUsername(dbLogin);
        hikariConfig.setDriverClassName(dbDriver);
        return hikariConfig;
    }

    /**
     * @return hikariDataSource.
     */
    @Bean
    public HikariDataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    /**
     * @return sqlSessionFactory.
     * @throws Exception, because we'll have exception situation.
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(hikariDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * @return userMapper.
     * @throws Exception, because we'll have exception situation.
     */
    @Bean
    public UserMapper userMapper() throws Exception {
        SqlSessionFactory sqlSessionFactory = sqlSessionFactory();
        sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate.getMapper(UserMapper.class);
    }

    /**
     * @return tagMapper.
     * @throws Exception, because we'll have exception situation.
     */
    @Bean
    public TagMapper tagMapper() throws Exception {
        SqlSessionFactory sqlSessionFactory = sqlSessionFactory();
        sqlSessionFactory.getConfiguration().addMapper(TagMapper.class);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate.getMapper(TagMapper.class);
    }

    /**
     * @return giftCertificateMapper.
     * @throws Exception, because we'll have exception situation.
     */
    @Bean
    public GiftCertificateMapper giftCertificateMapper() throws Exception {
        SqlSessionFactory sqlSessionFactory = sqlSessionFactory();
        sqlSessionFactory.getConfiguration().addMapper(GiftCertificateMapper.class);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate.getMapper(GiftCertificateMapper.class);
    }

    /**
     * @return tagGiftCertificateMapper.
     * @throws Exception, because we'll have exception situation.
     */
    @Bean
    public TagGiftCertificateMapper tagGiftCertificateMapper() throws Exception {
        SqlSessionFactory sqlSessionFactory = sqlSessionFactory();
        sqlSessionFactory.getConfiguration().addMapper(TagGiftCertificateMapper.class);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate.getMapper(TagGiftCertificateMapper.class);
    }

    /**
     * @return userGiftCertificateMapper.
     * @throws Exception, because we'll have exception situation.
     */
    @Bean
    public PurchaseMapper userGiftCertificateMapper() throws Exception {
        SqlSessionFactory sqlSessionFactory = sqlSessionFactory();
        sqlSessionFactory.getConfiguration().addMapper(PurchaseMapper.class);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate.getMapper(PurchaseMapper.class);
    }

    /**
     * Bean to create modelMapper.
     * @return modelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * @return resourceBundleMessageSource.
     */
    @Bean
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("lang");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return resourceBundleMessageSource;
    }

    /**
     * @return platformTransactionManager.
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(hikariDataSource());
    }
}