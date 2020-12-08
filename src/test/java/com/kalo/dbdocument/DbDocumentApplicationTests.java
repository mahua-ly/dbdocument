package com.kalo.dbdocument;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DbDocumentApplicationTests {

    @Autowired
    ApplicationContext applicationContext;
    //生成文档路径
    private static final String FILE_PATH = "D:\\doc";

    /**
     * TODO     数据库文档生成
     * @Title   contextLoads
     * @Author  Yin.YaoFeng 
     * @Return  void 
     * @Date    2020/12/8 0008 13:49
     */
    @Test
    void contextLoads() {
        //1. 生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径
                .fileOutputDir(FILE_PATH)
                // 打开目录
                .openOutputDir(false)
                //文件类型：HTML-html页面文档; WORD-Word文档; MD-Markdown文档; TODO 根据自己喜好选择生成文档格式
                .fileType(EngineFileType.WORD)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker)
                .build();

        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
        //2. 读取数据源配置
        DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);
        Configuration config = Configuration.builder()
                .version("1.0.0")
                //文档标题
                .title("卡罗-数据库表结构文档")
                //文件名
                .description("卡罗-数据库文档")
                .dataSource(dataSourceMysql)
                .engineConfig(engineConfig)
                .produceConfig(buildProcessConfig())
                .build();
        // 执行生成
        new DocumentationExecute(config).execute();
    }
    /**
     * TODO     生成表配置  配置想要生成的表+ 配置想要忽略的表
     * @Title   buildProcessConfig
     * @Author  Yin.YaoFeng
     * @Return  cn.smallbun.screw.core.process.ProcessConfig
     * @Date    2020/12/8 0008 13:50
     */
    public static ProcessConfig buildProcessConfig() {
        return ProcessConfig.builder()
                //全库生成
//                .designatedTableName(new ArrayList<>())
                //根据名称指定表生成
                .designatedTableName(Arrays.asList("wealth_c_userinfo"))
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表前缀生成：指定表前缀
//                .designatedTablePrefix(Arrays.asList("mall_shop_", "mall_order"))
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(new ArrayList<>())
//                .ignoreTableName(Arrays.asList("emp"))
                //忽略表前缀
                .ignoreTablePrefix(new ArrayList<>())
//                .ignoreTablePrefix(Arrays.asList("cost_","cust", "emp","sys_","tb_","user_","wx_","schedule_","enterprise_","ling_"))
                //忽略表后缀
                .ignoreTableSuffix(new ArrayList<>())
//                .ignoreTableSuffix(Arrays.asList("_test"))
                .build();
    }

}
