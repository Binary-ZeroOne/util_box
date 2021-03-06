package org.zero.utilitybox.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @ProjectName applicationBox
 * @Author: zeroJun
 * @Date: 2018/7/31 11:10
 * @Description: json工具类
 */
@Slf4j
public class JsonUtil {
    /**
     * ObjectMapper用于构建JsonNode节点树，这是最灵活的方法，它类似于XML的DOM解析器
     */
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String STANDARO_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {

        /*
         *  setSerializationInclusion用于设置序列化对象的哪些字段
         *  ALWAYS  序列化所有的字段
         *  NON_NULL  序列化除了null值以外的字段，也就是只序列化有值的字段
         *  NON_DEFAULT  不序列化有默认值的字段
         *  NON_EMPTY 不序列化为空值的字段，不局限于null值，比NON_NULL更严格
         */
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);

        // 取消默认将时间转换成timestamps（时间戳）格式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);

        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        // 忽略json字符串中的字段与java对象属性对应不上的错误情况。主要是防止报错
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 不序列化不知道的字段
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 把所有的日期格式都统一为：yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARO_FORMAT));

        // 设置过滤
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
    }

    /**
     * 将对象转换成JSON字符串，无格式
     *
     * @param src 需要序列化的对象
     * @param <T> 泛型
     * @return String
     */
    public static <T> String obj2String(T src) {
        if (src == null) {
            return null;
        }

        try {
            // 如果本身就是字符串类型就直接返回，否则转换成JSON字符串进行返回
            return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
        } catch (Exception e) {
            log.warn("Parse Object to String error. src : {}, e : {}", src, e);
            return null;
        }
    }

    /**
     * 序列化，将对象转换成JSON字符串，然后返回格式化好的JSON字符串
     *
     * @param src 需要序列化的对象
     * @param <T> 泛型
     * @return <T>
     */
    public static <T> String obj2StringPretty(T src) {
        if (src == null) {
            return null;
        }
        try {
            // 如果本身就是字符串类型就直接返回，否则转换成JSON字符串并将其格式化好后进行返回
            return src instanceof String ? (String) src : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(src);
        } catch (Exception e) {
            log.error("Parse Object to String error. src : {}, e : {}", src, e);
            return null;
        }
    }

    /**
     * 反序列化，将字符串转换成对象
     *
     * @param src src
     * @param cls 指定需要反序列化成哪种对象类型
     * @param <T> 泛型
     * @return T
     */
    public static <T> T string2Obj(String src, Class<T> cls) {
        if (StringUtils.isEmpty(src) || cls == null) {
            return null;
        }
        try {
            // 当类型本身就为字符串时，直接返回，否则转换成相应的类型进行返回
            return cls.equals(String.class) ? (T) src : objectMapper.readValue(src, cls);
        } catch (IOException e) {
            log.warn("Parse String to Object error. src : {}, cls : {}, error : {}", src, cls, e);
            return null;
        }
    }

    /**
     * 反序列化，将字符串转换成泛型里限定的对象，主要用于转换带有泛型的复杂对象
     *
     * @param src            src
     * @param tTypeReference 承载泛型限定的类型
     * @param <T>            泛型
     * @return T
     */
    public static <T> T string2Obj(String src, TypeReference<T> tTypeReference) {
        if (StringUtils.isEmpty(src) || tTypeReference == null) {
            return null;
        }
        try {
            // 当类型本身就为字符串时，直接返回，否则转换成相应的类型进行返回
            return (T) (tTypeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, tTypeReference));
        } catch (IOException e) {
            log.warn("Parse String to Object error. src : {}, tTypeReference : {}, error : {}", src, tTypeReference, e);
            return null;
        }
    }

    /**
     * 反序列化，将字符串转换成泛型里限定的对象，主要用于转换带有泛型的复杂对象
     *
     * @param str             str
     * @param collectionClass 集合类型
     * @param elementClasses  集合里元素的类型
     * @param <T>             泛型
     * @return <T>
     */
    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

        try {
            // 转换成相应的类型进行返回
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.error("Parse String to Object error. str : {}, collectionClass : {}, elementClasses : {}, error : {}",
                    str, collectionClass, elementClasses, e);
            return null;
        }
    }
}
