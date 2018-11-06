package com.fjnu.common.utils;

import java.math.BigDecimal;

/**
 * Created by LCC on 2018/5/13.
 */
public class CalculateUtils {

    /**
     * 将外币兑换成人民币
     *
     * @param originalPrice 外币
     * @param rate          人民币
     * @return 保留两位的人民币值
     */
    public static Float convertPriceToRMB(float originalPrice, BigDecimal rate) {
        BigDecimal price = new BigDecimal(Float.toString(originalPrice));
        return price.multiply(rate).setScale(2, BigDecimal.ROUND_DOWN).floatValue();
    }

    /**
     * 将外币兑换成人民币
     *
     * @param originalPrice 外币
     * @param rate          人民币
     * @return 保留两位的人民币值
     */
    public static Float convertPriceToRMB(double originalPrice, BigDecimal rate) {
        BigDecimal price = new BigDecimal(Double.toString(originalPrice));
        return price.multiply(rate.setScale(2, BigDecimal.ROUND_DOWN)).floatValue();
    }

    /**
     * 两个float相乘
     *
     * @param f1
     * @param f2
     * @return 保留两位的乘积
     */
    public static Float multiply(float f1, float f2) {
        BigDecimal number1 = new BigDecimal(f1);
        BigDecimal number2 = new BigDecimal(f2);
        return number1.multiply(number2).setScale(2, BigDecimal.ROUND_DOWN).floatValue();
    }

    /**
     * 除法运算
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 保留两位的商
     */
    public static Float divide(float dividend, float divisor) {
        BigDecimal number1 = new BigDecimal(dividend);
        BigDecimal number2 = new BigDecimal(divisor);
        return number1.divide(number2, 2, BigDecimal.ROUND_DOWN).floatValue();
    }

}
