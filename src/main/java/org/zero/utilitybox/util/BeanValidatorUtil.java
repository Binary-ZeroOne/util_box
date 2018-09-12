package org.zero.utilitybox.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.zero.utilitybox.common.ServerResponse;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * @program: permission-system
 * @description: 参数校检工具
 * @author: 01
 * @create: 2018-09-12 20:38
 **/
public class BeanValidatorUtil {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * 检验单个字段
     *
     * @param t      t
     * @param groups groups
     * @param <T>    泛型
     * @return map
     */
    public static <T> Map<String, String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validateResult = validator.validate(t, groups);

        if (validateResult.isEmpty()) {
            // 无错误时返回空的map
            return Collections.emptyMap();
        } else {
            // 有错误时返回包含错误信息的map
            return errorResult(validateResult);
        }
    }

    /**
     * 检验列表字段
     *
     * @param collection collection
     * @return map
     */
    public static Map<String, String> validateList(Collection<?> collection) {
        Preconditions.checkNotNull(collection);

        if (collection.isEmpty()) {
            // 无错误时返回空的map
            return Collections.emptyMap();
        }

        Map<String, String> errors = null;
        for (Object object : collection) {
            Class[] classes = new Class[0];
            errors = validate(object, classes);
        }
        return errors;
    }

    /**
     * 可校验单个字段及列表字段
     *
     * @param first   first
     * @param objects objects
     * @return map
     */
    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validateList(Lists.asList(first, objects));
        } else {
            Class[] classes = new Class[0];
            return validate(first, classes);
        }
    }

    /**
     * 检查参数，若有错误则抛出异常
     *
     * @param param param
     */
    public static void check(Object param) throws RuntimeException {
        Map<String, String> map = validateObject(param);
        if (MapUtils.isNotEmpty(map)) {
            throw new RuntimeException(map.toString());
        }
    }

    /**
     * 检查参数，若有错误返回错误信息
     *
     * @param param param
     */
    public static ServerResponse checkParam(Object param) {
        Map<String, String> map = validateObject(param);
        if (MapUtils.isNotEmpty(map)) {
            return ServerResponse.createByErrorMsg(map.toString());
        }

        return ServerResponse.createByCodeSuccess();
    }

    /**
     * 将错误信息封装成map返回
     *
     * @param validateResult validateResult
     * @param <T>            泛型
     * @return error map key是有问题的字段，value是错误信息
     */
    private static <T> Map<String, String> errorResult(Set<ConstraintViolation<T>> validateResult) {
        Map<String, String> errors = Maps.newLinkedHashMap();
        for (ConstraintViolation<T> tConstraintViolation : validateResult) {
            errors.put(tConstraintViolation.getPropertyPath().toString(), tConstraintViolation.getMessage());
        }

        return errors;
    }
}
