package ac.cn.saya.mqtt.middle.service;

import ac.cn.saya.mqtt.middle.entity.*;
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
    public Result<Integer> addIotWarningRules(IotWarningRulesEntity param);

    /**
     * @描述 修改告警规则
     * @参数
     * @返回值
     * @创建人  shmily
     * @创建时间  2020/7/29
     * @修改人和其它信息
     */
    public Result<Integer> editIotWarningRules(IotWarningRulesEntity param);

    /**
     * @描述 删除告警规则
     * @参数  [param]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> deleteIotWarningRules(IotWarningRulesEntity param);

    /**
     * @描述 分页查看终端告警规则
     * @参数  [productId]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Object> getIotWarningRules(int productId);

    /**
     * @Title   批量写入采集数据，本方法由mq消费端发起调用
     * @Params  datas
     * @Return  void
     * @Author  saya.ac.cn-刘能凯
     * @Date  4/11/21
     * @Description
     */
    public void insertCollectionData(List<IotCollectionEntity> datas);

    /**
     * @Title 根据采集的数据校验是否产生异常告警
     * @Params  [datas]
     * @Return  void
     * @Author  saya.ac.cn-刘能凯
     * @Date  4/11/21
     * @Description
     */
    public void checkRuleWarring(List<IotCollectionEntity> datas);

    /**
     * @Title 修改设备最后上报时间
     * @Params  [identifyUuid]
     * @Return  void
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/26/21
     * @Description
     */
    public void updateDeviceHeart(String identifyUuid);

    /**
     * @描述 查询指定设备最新的采集信息
     * @参数  [clientId]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    public Result<Object> getClientLatestCollect(int clientId);

}
