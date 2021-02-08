package com.backstage.system.entity.customized;

import com.backstage.system.entity.gen.VPrivilege;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;

/**
 * 应用对象 - VPrivilege.
 * <p>
 * 该类于 2018-06-21 10:07:50 首次生成，后由开发手工维护。
 * </p>
 *
 * @author yangfeng
 * @version 1.0.0, Jun 21, 2018
 */
@JsonSerialize(include = Inclusion.ALWAYS)
public final class VPrivilegeAO extends VPrivilege implements Serializable {

    /**
     * 默认的序列化 id.
     */
    private static final long serialVersionUID = 1L;
}
