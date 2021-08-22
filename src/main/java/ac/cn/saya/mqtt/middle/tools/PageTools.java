package ac.cn.saya.mqtt.middle.tools;

import ac.cn.saya.mqtt.middle.entity.BaseEntity;

import java.util.function.Function;

/**
 * @Title: PageTools
 * @ProjectName lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-03-05 21:29
 * @Description:分页助手
 */

public class PageTools {

    public static Result<Object> page(long count, BaseEntity condition, Function<BaseEntity,Object> dao){
        Paging paging = new Paging();
        if (condition.getNowPage() == null) {
            condition.setNowPage(1);
        }
        if (condition.getPageSize() == null) {
            condition.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(condition.getPageSize());
        if (count > 0) {
            //总记录数
            paging.setDateSum(count);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(condition.getNowPage());
            //设置行索引
            condition.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            paging.setGrid(dao.apply(condition));
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            return ResultUtil.error(ResultEnum.NOT_EXIST);
        }
    }

}
