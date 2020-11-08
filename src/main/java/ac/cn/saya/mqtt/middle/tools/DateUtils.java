package ac.cn.saya.mqtt.middle.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期时间工具类（jdk1.8）
 *
 * @Title: DateUtils
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-10-02 23:56
 * @Description: https://blog.csdn.net/qq32933432/article/details/87974071
 */

public class DateUtils {

    /**
     * 文件上传及目录创建生成时间
     */
    public static DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 日期格式
     */
    public final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 日期时间格式
     */
    public final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取当前日期时间
     *
     * @param dateFormat 时间格式 eg："yyyy-MM-dd HH:mm:ss"
     * @return 2019-10-03 00:05:32
     */
    public static String getCurrentDateTime(DateTimeFormatter dateFormat) {
        return LocalDateTime.now().format(dateFormat);
    }

    /**
     * @描述 获取今天星期几
     * @参数 date
     * @返回值 星期日i==1，星期六i==7
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/23
     * @修改人和其它信息
     */
    public static int getDayOfTheWeek(LocalDate date) {
        ///String[][] strArray = {{"SUNDAY", "日"},{"MONDAY", "一"}, {"TUESDAY", "二"}, {"WEDNESDAY", "三"}, {"THURSDAY", "四"}, {"FRIDAY", "五"}, {"SATURDAY", "六"}};
        String[][] strArray = {{"SUNDAY", "1"}, {"MONDAY", "2"}, {"TUESDAY", "3"}, {"WEDNESDAY", "4"}, {"THURSDAY", "5"}, {"FRIDAY", "6"}, {"SATURDAY", "7"}};
        String k = String.valueOf(date.getDayOfWeek());
        System.out.println("k:" + k);
        //获取行数
        for (int i = 0; i < strArray.length; i++) {
            if (k.equals(strArray[i][0])) {
                k = strArray[i][1];
                break;
            }
        }
        return Integer.valueOf(k);
    }

    /**
     * @描述 获取该月的天数
     * @参数 date eg：2019-06-10
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/23
     * @修改人和其它信息
     */
    public static int getLengthOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date, ac.cn.saya.laboratory.tools.DateUtils.dateFormat);
        return localDate.lengthOfMonth();
    }

    /**
     * @描述 获取该月第一天是星期几
     * @参数 date eg：2019-06-10
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/23
     * @修改人和其它信息
     */
    public static int getFirstDayWeek(String date) {
        LocalDate localDate = LocalDate.parse(date, ac.cn.saya.laboratory.tools.DateUtils.dateFormat);
        // 构造本月第一天
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return ac.cn.saya.laboratory.tools.DateUtils.getDayOfTheWeek(firstDay);
    }

    /**
     * @描述 获取指定月份的第一天
     * @参数 date eg：2019-06-10
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/23
     * @修改人和其它信息
     */
    public static String getFirstDayOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date, ac.cn.saya.laboratory.tools.DateUtils.dateFormat);
        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return firstDay.format(ac.cn.saya.laboratory.tools.DateUtils.dateFormat);
    }

    /**
     * @描述 获取指定月份的最后一天
     * @参数 date eg：2019-06-10
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/23
     * @修改人和其它信息
     */
    public static String getLastDayOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date, ac.cn.saya.laboratory.tools.DateUtils.dateFormat);
        LocalDate firstDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return firstDay.format(ac.cn.saya.laboratory.tools.DateUtils.dateFormat);
    }

    /**
     * 检查是否为当前月份
     *
     * @param date
     * @return 是 true
     */
    public static Boolean checkIsCurrentMonth(String date) {
        try {
            LocalDate paramDate = LocalDate.parse(date, ac.cn.saya.laboratory.tools.DateUtils.dateFormat);
            LocalDate currentDate = LocalDate.now();
            if ((paramDate.getYear()) == currentDate.getYear() && paramDate.getMonthValue() == currentDate.getMonthValue()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String datetime = ac.cn.saya.laboratory.tools.DateUtils.getCurrentDateTime(ac.cn.saya.laboratory.tools.DateUtils.dateTimeFormat);
        System.out.println("now-date:" + datetime);
        String firstDayOfMonth = ac.cn.saya.laboratory.tools.DateUtils.getFirstDayOfMonth("2019-10-10");
        System.out.println("firstDayOfMonth:" + firstDayOfMonth);
        String lastDayOfMonth = ac.cn.saya.laboratory.tools.DateUtils.getLastDayOfMonth("2019-10-10");
        System.out.println("lastDayOfMonth:" + lastDayOfMonth);
        System.out.println("本月天数:" + ac.cn.saya.laboratory.tools.DateUtils.getLengthOfMonth("2019-10-20"));
        System.out.println("本月第一天是:" + getFirstDayWeek("2019-10-20"));
        checkIsCurrentMonth("2020-04-20");
    }

}
