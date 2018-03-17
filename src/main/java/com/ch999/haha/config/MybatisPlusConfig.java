/*
 * Copyright (c) 2006-2017, Yunnan Sanjiu Network technology Co., Ltd.
 * 
 * All rights reserved.
 */
package com.ch999.haha.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.File;

@Configuration
public class MybatisPlusConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MybatisProperties properties;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    /**
     * 乐观锁，实体字段必须添加@Version
     */
    /*
     * @Bean
     * public OptimisticLockerInterceptor optimisticLockerInterceptor() {
     * return new OptimisticLockerInterceptor();
     * }
     */

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
     * 配置文件和mybatis-boot的配置文件同步
     *
     * @return
     */
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(dataSource);
        mybatisPlus.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }
        mybatisPlus.setConfiguration(properties.getConfiguration());
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            mybatisPlus.setPlugins(this.interceptors);
        }
        // MP 全局配置，更多内容进入类看注释
        GlobalConfiguration globalConfig = new GlobalConfiguration(new LogicSqlInjector());
        globalConfig.setDbType(DBType.MYSQL.name());
        // ID 策略 AUTO->`0`("数据库ID自增")
        // INPUT->`1`(用户输入ID") ID_WORKER->`2`("全局唯一ID") UUID->`3`("全局唯一ID")
        globalConfig.setIdType(2);
        globalConfig.setLogicDeleteValue("1");
        globalConfig.setLogicNotDeleteValue("0");

        globalConfig.setMetaObjectHandler(new MetaObjectHandler() {

            @Override
            public void insertFill(MetaObject metaObject) {
                // Object createTime = getFieldValByName("createTime",
                // metaObject);
                // if (createTime == null) {
                // setFieldValByName("createTime", new Date(), metaObject);
                // }
                // Object updateUserId = getFieldValByName("updateUserId",
                // metaObject);
                // if (updateUserId == null) {
                // setFieldValByName("updateUserId", 123456L, metaObject);
                // }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                // // setFieldValByName("updateTime", new Date(), metaObject);
                // setFieldValByName("updateUserId", 234567L, metaObject);
            }
        });

        mybatisPlus.setGlobalConfig(globalConfig);
        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisPlus.setConfiguration(mc);
        if (this.databaseIdProvider != null) {
            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
        }
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
        }
        return mybatisPlus;
    }

    /**
     * <p>
     * mysql 自动生成工具
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        String url = MybatisPlusConfig.class.getClassLoader().getResource("").getPath();
        File file = new File(url);
        String projectPath = file.getParentFile().getParentFile().getAbsolutePath();
        String srcPath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java";
        // System.out.println(url);
        // System.out.println(projectPath);
        System.out.println("源代码将生成到(包会自动创建)：" + srcPath);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(srcPath);
        gc.setServiceName("%sService");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("");
        gc.setKotlin(false);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {

            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                return super.processTypeConvert(fieldType);

            }

        });

        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("a123456+");
        dsc.setUrl("jdbc:mysql://gz-cdb-mfro5399.sql.tencentcdb.com:63292/hahaapi?characterEncoding=utf8");

        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setTablePrefix(new String[] { "adconfig_" });// 此处可以修改为您的表前缀

        // strategy.setNaming(NamingStrategy.removePrefixAndCamel(name,new
        // String[]{"t_"}));// 表名生成策略
        // strategy.setExclude(new String[] { "productinfo", "view_product_spec"
        // }); // 排除生成的表

        strategy.setInclude(new String[]{"adoptionfeedback"}); //
        // 需要生成的表

        // 字段名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //逻辑删除字段
        strategy.setLogicDeleteFieldName("isdel");
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.fcs.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "is_del",
        // "create_time", "update_time" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.fcs.demo.TestMapper");
        // 自定义 testservice 父类
        // strategy.setSuperServiceClass("com.fcs.demo.TestService");
        // 自定义 testservice 实现类父类
        // strategy.setSuperServiceImplClass("com.fcs.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.fcs.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuliderModel(true);
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.ch999.haha");
        pc.setModuleName("admin");
        pc.setController("controller");

        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        // InjectionConfig cfg = new InjectionConfig() {
        // @Override
        // public void initMap() {
        // Map<String, Object> map = new HashMap<String, Object>();
        // map.put("abc", this.getConfig().getGlobalConfig().getAuthor() +
        // "-mp");
        // this.setMap(map);
        // }
        // };
        // // 自定义 xxList.jsp 生成
        // List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // focList.add(new FileOutConfig("/template/list.jsp.vm") {
        // @Override
        // public String outputFile(TableInfo tableInfo) {
        // // 自定义输入文件名称
        // return "D://my_" + tableInfo.getEntityName() + ".jsp";
        // }
        // });
        // cfg.setFileOutConfigList(focList);
        // mpg.setCfg(cfg);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置
        // System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
