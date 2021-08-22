package ac.cn.saya.mqtt.middle.controller;

import ac.cn.saya.mqtt.middle.entity.IotAppointmentEntity;
import ac.cn.saya.mqtt.middle.entity.IotHistoryExecuteEntity;
import ac.cn.saya.mqtt.middle.service.CommandService;
import ac.cn.saya.mqtt.middle.tools.Result;
import ac.cn.saya.mqtt.middle.tools.ResultEnum;
import ac.cn.saya.mqtt.middle.tools.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: CommandController
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/11/8 22:23
 * @Description: TODO
 */
@RestController
@RequestMapping(value = "/backend")
public class CommandController {
    
    private final CommandService commandService;

    @Autowired
    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    /**
     * @描述 分页查看指令下发历史
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "command/history")
    public Result<Object> getIotHistoryExecutePage(IotHistoryExecuteEntity entity){
        return commandService.getIotHistoryExecutePage(entity);
    }

    /**
     * @描述 添加终端规则预约信息
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @PostMapping(value = "appointment")
    public Result<Integer> addIotAppointment(@RequestBody IotAppointmentEntity entity){
        if (null == entity || entity.getClientId() == null || StringUtils.isEmpty(entity.getName()) || StringUtils.isEmpty(entity.getExcuteTime()) || entity.getCommand() == null){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        return commandService.addIotAppointment(entity);
    }

    /**
     * @描述 修改终端规则预约信息
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @PutMapping(value = "appointment")
    public Result<Integer> editIotAppointment(@RequestBody IotAppointmentEntity entity){
        if (null == entity || entity.getId() == null){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        return commandService.editIotAppointment(entity);
    }

    /**
     * @描述 删除终端规则预约信息
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @DeleteMapping(value = "appointment/{id}")
    public Result<Integer> deleteIotAppointment(@PathVariable(value = "id")  Integer id){
        if (null == id || id <= 0){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        return commandService.deleteIotAppointment(id);
    }

    /**
     * @描述 终端规则预约信息分页
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "appointment")
    public Result<Object> getIotAppointmentPage(IotAppointmentEntity entity){
        return commandService.getIotAppointmentPage(entity);
    }
    
}
