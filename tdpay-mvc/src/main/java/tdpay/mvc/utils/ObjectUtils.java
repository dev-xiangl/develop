package tdpay.mvc.utils;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

/**
 * オブジェクトユーティリティクラス
 */
public class ObjectUtils {

    private ObjectUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    public static void copyProperties(Object src, Object target, Set<String> props) {
        if (CollectionUtils.isEmpty(props)) {
            copyProperties(src, target);
            return;
        }

        final String[] excludedProperties =
            Arrays.stream(BeanUtils.getPropertyDescriptors(src.getClass()))
                  .map(PropertyDescriptor::getName)
                  .filter(name -> !props.contains(name))
                  .toArray(String[]::new);

        BeanUtils.copyProperties(src, target, excludedProperties);
    }

    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target);
    }

    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T object) throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos).writeObject(object);
        return (T) new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())).readObject();
    }
}
