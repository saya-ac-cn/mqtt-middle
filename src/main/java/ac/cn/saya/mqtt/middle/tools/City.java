package ac.cn.saya.mqtt.middle.tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  根据IP地址获取详细的地域信息
 *  @project:personGocheck
 *  @class:City.java
 *  @author:刘能凯 E-mail:742442849@qq.com
 *  @date：2017-07-05
 */
public class City {

    /**
     *通过IP返回城市的完整信息（自抛出异常，不进行处理）
     *
     * @param content
     *            请求的参数 格式为：name=xxx&pwd=xxx
     * @param encodingString
     *            服务器端请求编码。如GBK,UTF-8等
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getAddresses(String content, String encodingString)
            throws UnsupportedEncodingException {
        if(content == null || content.equals("127.0.0.1") || content.equals("localhost") || content.equals("::1"))
        {
            return "局域网地址";
        }
        if(!isboolIp(content))
        {
            return "地址非法";
        }
        content = "ip=" + content;
        // 这里调用pconline的接口
        String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
        //String urlStr = "http://whois.pconline.com.cn";
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        String returnStr = this.getResult(urlStr, content, encodingString);
        if (returnStr != null) {
            // 处理返回的省市区信息-未处理的原始数据
            System.out.println(returnStr);
            String[] temp = returnStr.split(",");
            if(temp.length<3){
                //无效IP，局域网测试
                return "局域网地址";
            }
            String country = "";
            String area = "";
            String region = "";
            String city = "";
            String county = "";
            String isp = "";
            String city_id = "";
            for (int i = 0; i < temp.length; i++) {
                switch (i) {
                    case 2:
                        // 国家
                        country = (temp[i].split(":"))[1].replaceAll("\"", "");
                        country = decodeUnicode(country);
                        break;
                    case 3:
                        // 地区
                        area = (temp[i].split(":"))[1].replaceAll("\"", "");
                        area = decodeUnicode(area);
                        break;
                    case 4:
                        // 省
                        region = (temp[i].split(":"))[1].replaceAll("\"", "");
                        region = decodeUnicode(region);
                        break;
                    case 5:
                        // 市
                        city = (temp[i].split(":"))[1].replaceAll("\"", "");
                        city = decodeUnicode(city);
                        break;
                    case 7:
                        // 运营商
                        isp = (temp[i].split(":"))[1].replaceAll("\"", "");
                        isp = decodeUnicode(isp);
                        break;
                    case 11:
                        // 行政编码
                        city_id = (temp[i].split(":"))[1].replaceAll("\"", "");
                        city_id = decodeUnicode(city_id);
                        break;
                }
            }
            region=country+"-"+region+"-"+city+"("+city_id+")-"+county+"-"+isp;
            System.out.println(region);
            return region;
        }
        return "获取失败";
    }
    /**
     * @param urlStr
     *            请求的地址
     * @param content
     *            请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding
     *            服务器端请求编码。如GBK,UTF-8等
     * @return
     */
    private String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间，单位毫秒
            connection.setConnectTimeout(8000);
            // 设置读取数据超时时间，单位毫秒
            connection.setReadTimeout(8000);
            // 是否打开输出流 true|false
            connection.setDoOutput(true);
            // 是否打开输入流true|false
            connection.setDoInput(true);
            // 提交方法POST|GET
            connection.setRequestMethod("POST");
            // 是否缓存true|false
            connection.setUseCaches(false);
            // 打开连接端口
            connection.connect();
            // 打开输出流往对端服务器写数据
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());
            // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.writeBytes(content);
            // 刷新
            out.flush();
            // 关闭输出流
            out.close();
            // 往对端写完数据对端服务器返回数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), encoding));
            // ,以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }
    /**
     * unicode 转换成 中文
     *
     * @author fanhui 2007-3-15
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * 通过IP地址返回城市（自抛出异常，并自处理）
     * @param content
     * @param encodingString
     * @return
     */
    public String getCity(String content, String encodingString)
    {
        String address = "";
        try {
            address = this.getAddresses(content, encodingString);
        } catch (UnsupportedEncodingException e) {
            address="获取失败";
            e.printStackTrace();
        }finally {
           return address;
        }
    }

    /**
     * 判断ip地址格式是否正确
     * @param ipAddress
     * @return
     */
    public static boolean isboolIp(String ipAddress) {
        String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    // 测试
 public static void main(String[] args) {
  City addressUtils = new City();
  // 测试ip 119.29.153.69 中国=华南=广东省=广州市=越秀区=电信
  String ip = "117.139.255.111";
  String address = "";
  try {
   ///address = addressUtils.getAddresses(ip, "utf-8");
   address = addressUtils.getCity(ip,"utf-8");
  } catch (Exception e) {
   e.printStackTrace();
  }
  System.out.println(address);
  // 输出结果为：广东省,广州市,越秀区
 }


}
