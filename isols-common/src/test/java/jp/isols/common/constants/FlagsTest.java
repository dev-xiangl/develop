package jp.isols.common.constants;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlagsTest {

	@BeforeEach
    public void beforeEach() {
    }

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = Flags.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }
}
