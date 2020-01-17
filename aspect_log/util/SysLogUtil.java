package com.ppmoney.wm.trade.commons.log.util;

import com.ppmoney.wm.trade.commons.log.annotation.SysLog;
import com.ppmoney.wm.trade.commons.log.strategy.SensitiveStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 日志工具类
 *
 * @author jiangpeng
 */
@Component
@Slf4j
public class SysLogUtil {
    private static ApplicationContext applicationContext;

    @Autowired
    public void setComponent(ApplicationContext applicationContext) {
        SysLogUtil.applicationContext = applicationContext;
    }

    /***
     * 获取操作信息 Description
     * @param point 连接点
     * @return String
     */
    public static String getControllerMethodDescription(JoinPoint point) {
        try {
            SysLog sysLog = SysLogUtil.getAnnotationSysLog(point);
            return Objects.isNull(sysLog) ? "" : sysLog.description();
        } catch (ClassNotFoundException e) {
            log.error("获取SysLog注解信息异常：", e);
        }
        return "";
    }

    /**
     * 获取脱敏后的参数内容
     *
     * @param point 连接点
     * @return String
     */
    public static String getControllerMethodSensitiveParam(JoinPoint point) {
        try {
            SysLog sysLog = SysLogUtil.getAnnotationSysLog(point);

            if (Objects.isNull(sysLog)) {
                return "";
            }

            String[] sensitiveParams = ArrayUtils.addAll(sysLog.params(), sysLog.extParams());
            Class<?> sensitiveStrategy = sysLog.strategy();
            Object[] args = point.getArgs();

            return executeSensitive(Arrays.asList(sensitiveParams), args, sensitiveStrategy);
        } catch (ClassNotFoundException e1) {
            log.error("获取SysLog注解信息异常：", e1);
        } catch (IllegalAccessException e2) {
            log.error("获取参数对象的属性值异常：", e2);
        }
        return "";
    }

    /**
     * 执行敏感词脱敏
     *
     * @param sensitiveParams   要脱敏的字段
     * @param args              方法的请求参数
     * @param sensitiveStrategy 脱敏处理类
     * @return String
     * @throws IllegalAccessException 属性获取异常
     */
    private static String executeSensitive(List<String> sensitiveParams, Object[] args, Class<?> sensitiveStrategy) throws IllegalAccessException {
        StringBuilder paramsInfo = new StringBuilder();

        for (Object obj : args) {
            paramsInfo.append(fieldNameAndValue(sensitiveParams, obj, sensitiveStrategy));
        }
        return paramsInfo.toString();
    }

    /**
     * @param sensitiveParams   要脱敏的字段
     * @param obj               请求参数对象
     * @param sensitiveStrategy 脱敏处理类
     * @return String
     * @throws IllegalAccessException 属性获取异常
     */
    private static String fieldNameAndValue(List<String> sensitiveParams, Object obj, Class<?> sensitiveStrategy) throws IllegalAccessException {
        StringBuilder paramsInfo = new StringBuilder();
        Object bean = applicationContext.getBean(sensitiveStrategy);
        Method desMethod = ReflectionUtils.findMethod(SensitiveStrategy.class, "des", Object.class, String.class);
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            //设置是否允许访问，不是修改原来的访问权限修饰词。
            field.setAccessible(true);

            if (sensitiveParams.contains(field.getName())) {
                paramsInfo.append(field.getName()).append(":").append(ReflectionUtils.invokeMethod(desMethod,
                        bean, field.get(obj), field.getName()));
            } else {
                paramsInfo.append(field.getName()).append(":").append(field.get(obj));
            }
            paramsInfo.append(", ");
        }
        return paramsInfo.toString();
    }

    /**
     * 获取对应连接点上的注解内容
     *
     * @param point 连接点
     * @return SysLog
     * @throws ClassNotFoundException
     */
    private static SysLog getAnnotationSysLog(JoinPoint point) throws ClassNotFoundException {
        // 获取连接点目标类名
        String targetName = point.getTarget().getClass().getName();
        // 获取连接点签名的方法名
        String methodName = point.getSignature().getName();
        //获取连接点参数
        Object[] args = point.getArgs();
        //根据连接点类的名字获取指定类
        Class targetClass = Class.forName(targetName);
        //获取类里面的方法
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            boolean isAnnotationSysLog = method.getName().equals(methodName)
                    && method.getParameterTypes().length == args.length
                    && method.isAnnotationPresent(SysLog.class);
            if (isAnnotationSysLog) {
                return method.getAnnotation(SysLog.class);
            }
        }
        return null;
    }

    /**
     * 获取堆栈信息
     *
     * @param throwable exception
     * @return String
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
