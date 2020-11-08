package ac.cn.saya.mqtt.middle.service;

import ac.cn.saya.mqtt.middle.entity.IotWarningRulesEntity;
import ac.cn.saya.mqtt.middle.entity.IotCollectionEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningResultEntity;
import ac.cn.saya.mqtt.middle.tools.Result;

import java.util.List;

/**
 * @Title: CollectionService
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author shmily
 * @Date: 2020/8/2 10:45
 * @Description: 采集相关业务集，数据方向-> 上行到数据处理中心
 */

public interface CollectionService {

    /**
     * @描述  分页查看采集信息
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/2
     * @修改人和其它信息
     */
    public Result<Object> getIotCollectionPage(IotCollectionEntity entity);

    /**
     * @描述  分页查看告警报告信息
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/2
     * @修改人和其它信息
     */
    public Result<Object> getIotWarningResultPage(IotWarningResultEntity entity);

    /**
     * @描述 添加告警规则
     * @参数
     * @返回值
     * @创建人  shmily
     * @创建时间  2020/7/29
     * @修改人和其它信息
     */
    public Result<Integer> addIotWarningRules(List<IotWarningRulesEntity> list);

    /**
     * @描述 修改告警规则
     * @参数
     * @返回值
     * @创建人  shmily
     * @创建时间  2020/7/29
     * @修改人和其它信息
     */
    public Result<Integer> editIotWarningRules(List<IotWarningRulesEntity> list);

    /**
     * @描述 删除告警规则
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> deleteIotWarningRules(List<Integer> ids);

    /**
     * @描述 分页查看终端告警规则
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Object> getIotWarningRulesPage(IotWarningRulesEntity entity);
}
