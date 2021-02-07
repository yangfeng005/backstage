package com.backstage.system.entity.customized;

import com.backstage.system.entity.gen.TimeCost;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;

/**
 * 应用对象 - TimeCost.
 * <p>
 * 该类于 2019-12-04 14:11:44 首次生成，后由开发手工维护。
 * </p>
 * @author yangfeng
 * @version 1.0.0, Dec 04, 2019
 */
@JsonSerialize(include = Inclusion.ALWAYS)
public final class TimeCostAO extends TimeCost implements Serializable {

    /**
     * 默认的序列化 id.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
