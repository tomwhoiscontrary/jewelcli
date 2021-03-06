package com.lexicalscope.jewel.cli.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lexicalscope.jewel.cli.CliValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

public class TestOptionalOptionAlternative {
    @Test public void testOptionalOption() throws CliValidationException {
        final OptionalOptionAlternative result0 =
                CliFactory.parseArguments(OptionalOptionAlternative.class, new String[] { "--count", "3" });
        assertEquals(Integer.valueOf(3), result0.getCount());

        final OptionalOptionAlternative result1 =
                CliFactory.parseArguments(OptionalOptionAlternative.class, new String[] {});
        assertEquals(null, result1.getCount());
    }
}
