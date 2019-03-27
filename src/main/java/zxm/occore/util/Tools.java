package zxm.occore.util;

import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Tools {

    private static String ISO_8601_24H_FULL_FORMAT = "yyyyMMdd'T'HHmmssX";
    private static SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_24H_FULL_FORMAT);
    private static SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String ReturnJsonToDate(JSONObject dateMap, String format) {
        String returnDateSrc = "";
        int year = Integer.parseInt(dateMap.get("year").toString()) + 1900;
        String month = (Integer.parseInt(dateMap.get("month").toString()) + 1) >= 10 ?
                (Integer.parseInt(dateMap.get("month").toString()) + 1) + "" :
                "0" + (Integer.parseInt(dateMap.get("month").toString()) + 1);
        String day = Integer.parseInt(dateMap.get("date").toString()) >= 10 ?
                Integer.parseInt(dateMap.get("date").toString()) + "" :
                "0" + Integer.parseInt(dateMap.get("date").toString());
        String hours = Integer.parseInt(dateMap.get("hours").toString()) >= 10 ?
                Integer.parseInt(dateMap.get("hours").toString()) + "" :
                "0" + Integer.parseInt(dateMap.get("hours").toString());
        String minutes = Integer.parseInt(dateMap.get("minutes").toString()) >= 10 ?
                Integer.parseInt(dateMap.get("minutes").toString()) + "" :
                "0" + Integer.parseInt(dateMap.get("minutes").toString());
        String seconds = Integer.parseInt(dateMap.get("seconds").toString()) >= 10 ?
                Integer.parseInt(dateMap.get("seconds").toString()) + "" :
                "0" + Integer.parseInt(dateMap.get("seconds").toString());
        if ("hh:mi:ss".equals(format)) {
            returnDateSrc = hours + ":" + minutes + ":" + seconds;
        } else if ("yyyy-mm-dd".equals(format)) {
            returnDateSrc = year + "-" + month + "-" + day;
        } else if ("yyyy-mm-dd hh:mi:ss".equals(format)) {
            returnDateSrc = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
        } else if ("yyyy/mm/dd".equals(format)) {
            returnDateSrc = year + "/" + month + "/" + day;
        } else if ("yyyy/mm/dd hh:mi:ss".equals(format)) {
            returnDateSrc = year + "/" + month + "/" + day + " " + hours + ":" + minutes + ":" + seconds;
        }
        return returnDateSrc;
    }

    /**
     * 毫秒转日期
     *
     * @param millionSeconds
     * @return
     */
    public static Date MillionSecondsToDate(String millionSeconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(millionSeconds));
        Date date = c.getTime();
        return date;
    }

    /**
     * MD5加密
     *
     * @param inStr
     * @return
     * @throws Exception
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 验证用户登陆密码
     *
     * @param userPassWord
     * @param passWord
     * @return
     */
    public static Boolean CheckUserPassWord(String userPassWord, String passWord) {
        Boolean result = false;
        try {
            if (md5Encode(userPassWord).equals(passWord)) {
                result = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 信息提示
     *
     * @param redirectAttributes
     * @param messages
     */
    public static void addMessage(RedirectAttributes redirectAttributes, String messages) {
        redirectAttributes.addFlashAttribute("message", messages);
    }

    /**
     * 返回时间格式
     *
     * @param dateTime
     * @return
     */
    public static String ReturnDateFormat(Date dateTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return timeFormat.format(dateTime);
    }

    public static String ReturnDateFormat(String dateTime) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = timeFormat.parse(dateTime);
        return timeFormat.format(date);
    }

    /**
     * 保存图片
     *
     * @param file     要保存的文件
     * @param headPath 文件的目录名
     */
    public static String saveFile(MultipartFile file, String headPath, String path) throws Exception {
        // 获取上传的图片名称
        String fileName = file.getOriginalFilename();
        // 声明新的图片名称
        String newImgName = "";
        // 图片保存的路径
        String img_path = path;
        File fileDir = new File(img_path + headPath);
        // 如果文件夹不存在则创建
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            // fileDir.mkdir();创建单级级目录
            fileDir.mkdirs();// 创建多级目录
        }

        if (file != null && fileName != null && fileName.length() > 0) {
            newImgName = headPath + UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
            // 创建新图片文件
            File imgFile = new File(img_path + newImgName);
            // 将内存中的图片写入硬盘
            file.transferTo(imgFile);
            // 给传进来的员工对象设置头像图片路径
        }
        return newImgName;
    }

    public static boolean deleteFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 更加数据流id返回数据类型
     *
     * @param type
     * @return
     */
    public static String ReturnDataTypeName(String type) {
        String result = "未知";
        switch (type) {
            case "temperature":
                result = "温度";
                break;
            case "pressure":
                result = "压力";
                break;
            case "voltage":
                result = "电压";
                break;
            case "current":
                result = "电流";
                break;
            case "light":
                result = "亮度";
                break;
        }

        return result;
    }

    /**
     * 根据数据流id返回数据单位
     *
     * @param type
     * @return
     */
    public static String ReturnDataTypeUnit(String type) {
        String result = "未知";
        switch (type) {
            case "temperature":
                result = "°C";
                break;
            case "pressure":
                result = "P";
                break;
            case "voltage":
                result = "V";
                break;
            case "current":
                result = "A";
                break;
            case "light":
                result = "lx";
                break;
        }
        return result;
    }

    /**
     * 格式化onenet 时间（yyyy-MM-dd HH:mm:ss.SSS -->> yyyyMMdd'T'HHmmss.SSS）
     *
     * @param dateSrc
     * @return
     */
    public static String onenetTime(String dateSrc) {// 20151212T121212Z
        String result = "";
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat timeFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = timeFormat.parse(dateSrc);
            result = timeFormat1.format(date);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return result;
    }

    /**
     * 格式化OceanConnect 时间
     *
     * @param dateSrc
     * @return
     */
    public static String UTCToLocal(String dateSrc) {// 20151212T121212Z
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        localFormater.setTimeZone(TimeZone.getDefault());
        String result = "";
        try {
            Date UtcDate = sdf.parse(dateSrc);
            result = localFormater.format(UtcDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date UTCToLocalDate(String dateSrc) {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        localFormater.setTimeZone(TimeZone.getDefault());
        try {
            Date UtcDate = sdf.parse(dateSrc);
            return UtcDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 本地时间转UTC时间（yyyy-MM-dd HH:mm:ss -->> yyyyMMdd'T'HHmmss'Z'）
     *
     * @param localTime
     * @return
     */
    public static String localToUTC(String localTime) {
        Date localDate = null;
        try {
            localDate = localFormater.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        return localFormater.format(utcDate);
    }

    /**
     * 返回时间差（分钟）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int ReturnTimeDifference(String startTime, String endTime) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long from;
        long to;
        try {
            from = simpleFormat.parse(startTime).getTime();
            to = simpleFormat.parse(endTime).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            from = 0;
            to = 0;
        }
        int minutes = (int) ((to - from) / (1000 * 60));
        return minutes;
    }

    /**
     * 返回OceanConnect 得时间格式
     *
     * @param dateSrc
     * @return
     */
    public static String TurnCODateFormat(String dateSrc) {
        String result = dateSrc.replace(" ", "T").replace("-", "").replace(":", "");
        if (result.length() == 13) {
            result += "00";
        }
        result += "Z";
        return result;
    }

    /**
     * 返回float小数2位
     *
     * @param cuNum
     * @return
     */
    public static String ReturnDevceiTypeFloatTwo(String cuNum) {
        String result = "";
        if (cuNum != null && !cuNum.equals("-")) {
            float num = Float.parseFloat(cuNum);
            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
            result = df.format(num);
        } else {
            result = "-";
        }
        return result;
    }

//    public String getDevicesStatus(){
//
//    }

}
