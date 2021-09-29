package ac.cn.saya.mqtt.middle.controller;

import ac.cn.saya.mqtt.middle.entity.IotCollectionEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningResultEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningRulesEntity;
import ac.cn.saya.mqtt.middle.service.CollectionService;
import ac.cn.saya.mqtt.middle.tools.Result;
import ac.cn.saya.mqtt.middle.tools.ResultEnum;
import ac.cn.saya.mqtt.middle.tools.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @描述 查看所有的告警定义
     * @参数
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "system/rule")
    public Result<Object> getRules(@RequestParam(value = "clientId",required = false) Integer clientId){
        return collectionService.getWarningRule(clientId);
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
        if (null == param){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
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
        if (null == param){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
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
    @DeleteMapping(value = "warning/rules/{ruleId}")
    public Result<Integer> deleteIotWarningRules(@PathVariable("ruleId") Integer ruleId){
        return collectionService.deleteIotWarningRules(ruleId);
    }

    /**
     * @描述 分页查看终端告警规则
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "warning/rules")
    public Result<Object> getIotWarningRulesPage(IotWarningRulesEntity entity){
        return collectionService.getIotWarningRulesPage(entity);
    }
    
}