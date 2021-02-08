package com.backstage.system.entity.customized;

import com.backstage.system.entity.gen.Resource;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;
import java.util.List;

/**
 * 应用对象 - Resource.
 * <p>
 * 该类于 2018-06-14 13:25:21 首次生成，后由开发手工维护。
 * </p>
 *
 * @author yangfeng
 * @version 1.0.0, Jun 14, 2018
 */
@JsonSerialize(include = Inclusion.ALWAYS)
@Data
public final class ResourceAO extends Resource implements Serializable {

    /**
     * 默认的序列化 id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 资源权限
     */
    private List<VPrivilegeAO> privileges;
}
