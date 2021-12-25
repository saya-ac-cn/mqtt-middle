package ac.cn.saya.mqtt.middle.repository.primary;
import ac.cn.saya.mqtt.middle.entity.IotClientEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Iot终端表(IotClient)表数据库访问层
 *
 * @author saya
 * @since 2020-07-19 13:24:33
 */
@Mapper
public interface IotClientDAO {

    /**
     * 查询Iot终端
     *
     * @param entity
     * @return 实例对象
     */
    public IotClientEntity query(IotClientEntity entity);

    /**
     * 下拉列表显示Iot终端
     * @param source 所属用户
     * @param keyWord 通过关键词，按照网关名或者设备名检索
     * @return
     */
    public List<IotClientEntity> querySelectList(@Param("source") String source,@Param("keyWord") String keyWord);

    /**
     * 分页查询Iot终端
     *
     * @param entity
     * @return 对象列表
     */
    public List<IotClientEntity> queryPage(IotClientEntity entity);


    /**
     * 通过产品id查询设备信息
     * @param productId
     * @return
     */
    public List<IotClientEntity> queryClientByProductId(@Param("productId")int productId);


    /**
     * 查询Iot终端数量
     *
     * @param entity 实例对象
     * @return 对象列表
     */
    public Long queryCount(IotClientEntity entity);

    /**
     * 新增Iot终端
     *
     * @param iotClient 实例对象
     * @return 影响行数
     */
    public int insert(IotClientEntity iotClient);

    /**
     * 修改Iot终端
     *
     * @param iotClient 实例对象
     * @return 影响行数
     */
    public int update(IotClientEntity iotClient);

    /**
     * 删除Iot终端
     *
     * @param id 主键
     * @return 影响行数
     */
    public int deleteById(@Param("id") int id);

    /**
     * 修改设备最后上线时间
     * @param id 设备id
     * @return
     */
    public int updateHeart(@Param("identifyUuid") String id);
}