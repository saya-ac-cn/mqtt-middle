package ac.cn.saya.mqtt.middle.controller;

import ac.cn.saya.mqtt.middle.entity.IotCollectionEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningResultEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningRulesEntity;
import ac.cn.saya.mqtt.middle.service.CollectionService;
import ac.cn.saya.mqtt.middle.tools.Result;
import ac.cn.saya.mqtt.middle.tools.ResultEnum;
import ac.cn.saya.mqtt.middle.tools.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @Title: CollectionController
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/11/8 22:16
 * @Description: TODO
 */
@RestController
@RequestMapping(value = "/backend")
public class CollectionController {
    
    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    /**
     * @描述 分页查看采集信息
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "collection")
    public Result<Object> getIotCollectionPage(IotCollectionEntity entity){
        return collectionService.getIotCollectionPage(entity);
    }

    /**
     * @描述 分页查看告警报告信息
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "warning/result")
    public Result<Object> getIotWarningResultPage(IotWarningResultEntity entity){
        return collectionService.getIotWarningResultPage(entity);
    }

    /**
     * @描述 添加告警规则
     * @参数  [param]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @PostMapping(value = "warning/rules")
    public Result<Integer> addIotWarningRules(@RequestBody IotWarningRulesEntity param){
        return collectionService.addIotWarningRules(param);
    }

    /**
     * @描述 修改告警规则
     * @参数  [param]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @PutMapping(value = "warning/rules")
    public Result<Integer> editIotWarningRules(@RequestBody IotWarningRulesEntity param){
        return collectionService.editIotWarningRules(param);
    }

    /**
     * @描述 删除告警规则
     * @参数  [list]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @DeleteMapping(value = "warning/rules")
    public Result<Integer> deleteIotWarningRules(IotWarningRulesEntity param){
        return collectionService.deleteIotWarningRules(param);
    }

    /**
     * @描述
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "warning/rules/{productId}")
    public Result<Object> getIotWarningRules(@PathVariable(value = "productId") int productId){
        return collectionService.getIotWarningRules(productId);
    }

    /**
     * @描述 查询指定设备最新的采集信息
     * @参数  [client]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    @GetMapping(value = "collection/latest/{client}")
    public Result<Object> getClientLatestCollect(@PathVariable(value = "client") Integer client){
        return collectionService.getClientLatestCollect(client);
    }
    
}