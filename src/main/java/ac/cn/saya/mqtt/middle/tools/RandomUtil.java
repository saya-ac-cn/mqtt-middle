package ac.cn.saya.mqtt.middle.tools;


import java.time.LocalDateTime;
import java.util.Random;

/**
 * @Title: RandomUtil
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/11 20:22
 * @Description: 随机文件命名
 */

public class RandomUtil {

    public final static String keysSource = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";


    /**
     * 生成随机文件名：当前年月日+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        LocalDateTime now = LocalDateTime.now();
        String str = now.format(DateUtils.fileFormat);
        Random random = new Random();
        // 获取5位随机数
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
        return str + rannum;
    }

    /**
     * 生成随机的iot名字
     *
     * @return
     */
    public static String getRandomIotName() {
        LocalDateTime now = LocalDateTime.now();
        String str = now.format(DateUtils.fileFormat);
        Random random = new Random();
        // 获取6位随机数
        int rannum = (int) (random.nextDouble() * (999999 - 100000 + 1)) + 100000;
        return str + rannum;
    }

    /**
     * 生成指定长度的密码
     */
    public static String getRandKeys(int intLength) {

        StringBuilder retStr;      //生成的密码
        //String keysSource = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz!@#$%^&*<>/.,";
        //密码使用符号，可更改

        int len = keysSource.length();
        //生成结束标志
        boolean bDone = false;
        do {
            retStr = new StringBuilder();
            //生成密码中数字的个数
            int count = 0;
            //生成密码中字母的个数
            int count1 = 0;
            //生成密码中符号的个数
            int count2 = 0;

            for (int i = 0; i < intLength; i++) {
                int intR = (int) Math.floor(Math.random() * len);
                //找到指定字符
                char c = keysSource.charAt(intR);

                //判断字符类型并计数：数字，字母，符号
                if (('0' <= c) && (c <= '9')) {
                    count++;
                } else if (('A' <= c) && (c <= 'z')) {
                    count1++;
                } else {
                    count2++;
                }
                retStr.append(keysSource.charAt(intR));
            }
            if (count >= 1 && count1 >= 4) {
                //如果符号密码强度，则置结束标志：密码至少包含1个数字，4个字母，1个符号
                bDone = true;
            }
        } while (!bDone);

        return retStr.toString();
    }


    public static void main(String[] args) {
        String fileName = RandomUtil.getRandomFileName();
        //2014030788359
        System.out.println(fileName);
    }

}
