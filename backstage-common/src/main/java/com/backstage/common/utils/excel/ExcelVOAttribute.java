package com.backstage.common.utils.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导入导出属性配置
 *
 * @author yangfeng
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ExcelVOAttribute {

    /**
     * 导出到Excel中的名字.
     */
    String name();

    /**
     * 配置列的名称,对应A,B,C,D....
     */
    String column() default "";

    /**
     * 指定导出列宽（以字符宽度的1/256为单位，假如你想显示5个字符的话，就可以设置5*256，1个汉字占2个字符）
     */
    int width() default 10 * 256;

    /**
     * 读取excel单元格值的长度.
     */
    int valueLength() default -1;

    /**
     * 导出字段字段排序（升序）
     */
    int sort() default 0;

    /**
     * 是否合并该列单元格
     *
     * @return
     */
    boolean isMergeColumn() default false;

    /**
     * 是否自动换行
     *
     * @return
     */
    boolean isWrapper() default false;

    /**
     * 是否不展示
     *
     * @return
     */
    boolean isHidden() default false;

    /**
     * 是否是关键字段，如果是，则该字段必须有值
     */
    boolean keyAttr() default false;

    /**
     * 提示信息
     */
    String prompt() default "";

    /**
     * 设置只能选择不能输入的列内容.
     */
    String[] combo() default {};

    /**
     * 如果字段实际是日期时间字段类型，定义日期时间的格式，如：yyyy/MM/dd
     */
    String dateFormat() default "";

    /**
     * 是否导出数据,应对需求:有时我们需要导出一份模板,这是标题需要但内容需要用户手工填写.
     */
    boolean isExport() default true;

}