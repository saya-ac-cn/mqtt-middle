package ac.cn.saya.mqtt.middle.entity;

import java.io.Serializable;

/**
 * @Title: BaseEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2018/8/11 11:47
 * @Description: 基础实体
 */

public class BaseEntity implements Serializable {

    /**
     * 请求页面
     */
    private Integer nowPage;
    /**
     * 请求页宽
     */
    private Integer pageSize;
    /**
     * 开始行->放入到sql中执行的
     */
    private Integer startLine;
    /**
     * 结束行->放入到sql中执行的
     */
    private Integer endLine;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;

    public BaseEntity() {
    }

    /**
     * 设置开始记录数索引和结束索引
     */
    public void setPage(Integer startLine,Integer endLine)
    {
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartLine() {
        return startLine;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public String getStartTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
