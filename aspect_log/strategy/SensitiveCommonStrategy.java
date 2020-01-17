package com.ppmoney.wm.trade.commons.log.strategy;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author jiangpeng
 * @date 2019/12/2312:24
 */
@Component
public class SensitiveCommonStrategy implements SensitiveStrategy {
    private static final String MASK = "****";

    @Override
    public String des(Object fieldValue, String fieldName) {
        return Objects.isNull(fieldValue) ? "" : mask(fieldValue.toString());
    }

    /**
     * 给指定字符串脱敏, 即对中间字符进行打打码
     *
     * @param source 要脱敏处理的字符串, 中间四位换成****
     * @return 脱敏后的字符串
     */
    private static String mask(String source) {
        return mask(source, MASK);
    }

    /**
     * 给指定字符串脱敏, 即对中间字符进行打打码
     *
     * @param source 要脱敏处理的字符串
     * @param mask   替换字符,比如****
     * @return 脱敏后的字符串
     */
    private static String mask(String source, String mask) {
        if (source == null || source.length() == 0) {
            return source;
        }
        int s = source.length();
        int m = mask.length();

        int idx = (s - m) / 2;
        if (idx > 0) {
            return source.substring(0, idx) + mask + source.substring(idx + m, s);
        }
        return mask;
    }
}
