package onetreeshopsapp.com.onetreeshops.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberFormatUtil {
    /**
     * Double
     *
     * @param str
     * @return
     */
    public static double ParseDouble(String str) {
        if (null == str || "".equals(str) || "null".equals(str)) {
            return 0.0;
        }
        return Double.parseDouble(str);
    }

    /**
     * int
     *
     * @param str
     * @return
     */
    public static int ParseInt(String str) {
        if (null == str || "".equals(str) || "null".equals(str) || "false".equals(str)) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    /**
     * double
     *
     * @return
     */
    public static double formatToDouble1(double num) {
//        NumberFormat nf = NumberFormat.getNumberInstance();
//        // 保留1位小数
//        nf.setMaximumFractionDigits(1);
//        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
//        nf.setRoundingMode(RoundingMode.UP);
//        return Double.parseDouble(nf.format(num));
        DecimalFormat df = new DecimalFormat("#.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String str = df.format(num);
        return Double.parseDouble(str);
    }

    /**
     * @return
     */
    public static double formatToDouble1(String num) {
        if (null == num || "".equals(num) || "null".equals(num)) {
            return 0.0;
        }
//        NumberFormat nf = NumberFormat.getNumberInstance();
//        // 保留1位小数
//        nf.setMaximumFractionDigits(1);
//        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
//        nf.setRoundingMode(RoundingMode.UP);
//        return Double.parseDouble(nf.format(num));
        DecimalFormat df = new DecimalFormat("#.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String str = df.format(ParseDouble(num));
        return Double.parseDouble(str);
    }

    /**
     * double
     *
     * @return
     */
    public static double formatToDouble2(double num) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String str = df.format(num);
        return Double.parseDouble(str);
    }

    /**
     * @return
     */
    public static double formatToDouble2(String num) {
        if (null == num || "".equals(num) || "null".equals(num)) {
            return 0.00;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String str = df.format(ParseDouble(num));
        return Double.parseDouble(str);
    }

    public static double formatToDouble3(String num) {
        if (null == num || "".equals(num) || "null".equals(num)) {
            return 0.0;
        }
        DecimalFormat df = new DecimalFormat("##0.000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String str = df.format(ParseDouble(num.trim()));
        return Double.parseDouble(str);
    }

    public static String formatToDouble3(double num) {
        DecimalFormat df = new DecimalFormat("##0.000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String str = df.format(num);
        return str;
    }

    //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
    //可以判断正负、整数小数
    //?:0或1个, *:0或多个, +:1或多个
    public static boolean isDouble(String str) {
        Boolean strResult = str.matches("^[- \\d][ \\d][\\d][\\.]\\d{3}$");
        return strResult;
    }

}
