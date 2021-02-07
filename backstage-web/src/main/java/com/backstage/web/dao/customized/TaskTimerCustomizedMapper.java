package com.backstage.web.dao.customized;

import com.backstage.web.entity.customized.TaskTimerAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TaskTimer 数据存取接口.
 *
 * <p>
 * 该类于 2019-08-29 15:33:35 生成，请勿手工修改！
 * </p>
 *
 * @author yangfeng
 * @version 1.0.0, Aug 29, 2019
 */
public interface TaskTimerCustomizedMapper {

    /**
     * 查询
     *
     * @param dm 机场代码
     * @return
     */
    List<TaskTimerAO> listByCondition(@Param("dm") String dm);
}
