package org.mziuri.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {
    @Test
    void testValidMessage() {
        assertTrue(Validation.getInstance().isMessageValid("Hello World"));
    }

    @Test
    void testInvalidMessage() {
        assertFalse(Validation.getInstance().isMessageValid("Hello\nWorld"));
    }
}