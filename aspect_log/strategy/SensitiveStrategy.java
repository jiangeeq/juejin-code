package com.ppmoney.wm.trade.commons.log.strategy;

/**
 * 定义一个函数式接口
 * 脱敏策略
 * @author jiangpeng
 */
@FunctionalInterface
public interface SensitiveStrategy {
    /**
     * 脱敏
     * @param fieldValue 原始内容
     * @param fieldName 字段名
     * @return 脱敏后的字符串
     */
    String des(final Object fieldValue, final String fieldName);
}
