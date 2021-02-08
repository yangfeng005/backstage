package com.backstage.system.entity.customized;

import com.backstage.system.entity.gen.Agency;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;

/**
 * 应用对象 - Agency.
 * <p>
 * 该类于 2019-11-11 09:09:51 首次生成，后由开发手工维护。
 * </p>
 *
 * @author yangfeng
 * @version 1.0.0, Nov 11, 2019
 */
@JsonSerialize(include = Inclusion.ALWAYS)
@Data
public final class AgencyAO extends Agency implements Serializable {

    /**
     * 默认的序列化 id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 父类名称
     */
    private String parentName;
}
