package com.backstage.system.entity.customized;

import com.backstage.system.entity.gen.SysExecException;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;

/**
 * 应用对象 - SysExecException.
 * <p>
 * 该类于 2019-12-27 14:14:13 首次生成，后由开发手工维护。
 * </p>
 *
 * @author yangfeng
 * @version 1.0.0, Dec 27, 2019
 */
@JsonSerialize(include = Inclusion.ALWAYS)
public final class SysExecExceptionAO extends SysExecException implements Serializable {

    /**
     * 默认的序列化 id.
     */
    private static final long serialVersionUID = 1L;
}
