package uk.co.flamingpenguin.jewel.cli;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException.ValidationError.ErrorType;
import uk.co.flamingpenguin.jewel.cli.examples.RmExample;

public class TestArgumentValidatorImpl {
    public interface NoValue {
        @Option boolean getName0();

        @Option boolean getName1();
    }

    public interface SingleValue {
        @Option String getName();
    }

    public interface MultipleValue {
        @Option List<String> getName();

        @Unparsed List<String> getUnparsed();
    }

    public interface ExtraValue {
        @Option List<String> getName0();

        @Option String getName1();

        @Unparsed List<String> getUnparsed();
    }

    public interface OptionalOption {
        @Option String getName0();

        @Option String getName1();
        boolean isName1();
    }

    public interface OptionAndUnparsed {
        @Option String getName0();

        @Unparsed List<String> getRemainingArguments();
    }

    @Test public void testMissingOption() {
        try {
            validate(new String[] { "--name1", "value" }, OptionalOption.class);
            fail();
        } catch (final ArgumentValidationException e) {
            final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
            assertEquals(1, validationErrors.size());
            assertEquals(ErrorType.MissingOption, validationErrors.get(0).getErrorType());
        }
    }

    @Test public void testMultipleValue() throws ArgumentValidationException {
        validate(new String[] { "--name", "a", "b" }, MultipleValue.class);
    }

    @Test public void testMultipleValueEndOfArguments() throws ArgumentValidationException {
        final ArgumentCollectionImpl validated =
                validate(new String[] { "--name", "a", "b", "--", "c", "d" }, MultipleValue.class);
        assertEquals(2, validated.getUnparsed().size());
        assertEquals(2, validated.getValues("name").size());
    }

    @Test public void testMultipleValueNotEndOfArguments() throws ArgumentValidationException {
        final ArgumentCollectionImpl validated =
                validate(
                        new String[] { "--name0", "a", "b", "--name1", "c", "d", "e", "--", "f", "g" },
                        ExtraValue.class);
        assertEquals(4, validated.getUnparsed().size());
        assertEquals(2, validated.getValues("name0").size());
        assertEquals(1, validated.getValues("name1").size());
    }

    @Test public void testAdjacentShortOptions() throws ArgumentValidationException {
        final ArgumentCollection validated = validate(new String[] { "-vrf", "./" }, RmExample.class);
        assertEquals(1, validated.getUnparsed().size());
    }

    @Test public void testSingleValue() throws ArgumentValidationException {
        validate(new String[] { "--name", "a" }, MultipleValue.class);
    }

    @Test public void testExtraOption() {
        try {
            validate(new String[] { "--name1", "value", "wrong", "--name0" }, ExtraValue.class);
            fail();
        } catch (final ArgumentValidationException e) {
            final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
            assertEquals(1, validationErrors.size());
            assertEquals(ErrorType.AdditionalValue, validationErrors.get(0).getErrorType());
        }
    }

    @Test public void testMissingValue() {
        try {
            validate(new String[] { "--name" }, SingleValue.class);
            fail();
        } catch (final ArgumentValidationException e) {
            final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
            assertEquals(1, validationErrors.size());
            assertEquals(ErrorType.MissingValue, validationErrors.get(0).getErrorType());
        }
    }

    @Test public void testUnexpectedValue() {
        try {
            validate(new String[] { "--name1", "value", "--name0" }, NoValue.class);
            fail();
        } catch (final ArgumentValidationException e) {
            final ArrayList<ValidationError> validationErrors = e.getValidationErrors();
            assertEquals(1, validationErrors.size());
            assertEquals(ErrorType.UnexpectedValue, validationErrors.get(0).getErrorType());
        }
    }

    @Test public void testMissingMultipleValue() throws ArgumentValidationException {
        validate(new String[] { "--name" }, MultipleValue.class);
        // TODO[tim]:support minimum/maximum value list lengths
    }

    @Test public void testOptionAndUnparsed() throws ArgumentValidationException {
        final ArgumentCollectionImpl validated =
                validate(new String[] { "--name0", "value0", "remaining0" }, OptionAndUnparsed.class);
        assertEquals(1, validated.getUnparsed().size());
    }

    private <O> ArgumentCollectionImpl validate(final String[] arguments, final Class<O> klass)
            throws ArgumentValidationException {
        final ArgumentValidatorImpl<O> impl =
                new ArgumentValidatorImpl<O>(
                        InterfaceOptionsSpecificationParser.<O>createOptionsSpecificationImpl(type(klass)));
        return (ArgumentCollectionImpl) impl.validateArguments(new ArgumentParserImpl().parseArguments(arguments));
    }
}
