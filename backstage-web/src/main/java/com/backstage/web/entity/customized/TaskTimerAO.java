package com.backstage.web.entity.customized;

import com.backstage.web.entity.gen.TaskTimer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;

/**
 * 应用对象 - TaskTimer.
 * <p>
 * 该类于 2019-08-29 14:30:37 首次生成，后由开发手工维护。
 * </p>
 * @author yangfeng
 * @version 1.0.0, Aug 29, 2019
 */
@JsonSerialize(include = Inclusion.ALWAYS)
public final class TaskTimerAO extends TaskTimer implements Serializable {

    /**
     * 默认的序列化 id.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
