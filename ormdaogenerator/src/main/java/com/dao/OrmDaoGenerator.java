package com.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class OrmDaoGenerator
{

    public static void main(String[] args) throws Exception
    {
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径
        Schema schema = new Schema(1, "com.hubin.android.p2pvoice.bean.dao");

        // 可以分别指定生成的 Bean 与 DAO 类所在的目录
//      Schema schema = new Schema(1, "me.itangqi.bean");
//      schema.setDefaultJavaPackageDao("me.itangqi.dao");

        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        // schema.enableActiveEntitiesByDefault();
        // schema.enableKeepSectionsByDefault();

        // 添加实体（Entities）
        addNote(schema);

        // 自动生成代码
        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Pointer」（既类名）
        Entity note = schema.addEntity("Pointer");
        // 重新给表命名
        // note.setTableName("NODE");

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 设置表中的字段：
        note.addIdProperty();
        note.addDateProperty("createDate");
        note.addStringProperty("name");
        note.addStringProperty("ip").notNull();
        note.addIntProperty("port");
        note.addIntProperty("audioSampleRate");
        note.addBooleanProperty("isRecordSend");
        note.addBooleanProperty("isRecordReceive");
    }
}
