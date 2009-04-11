/*
 * Copyright 2007 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.flamingpenguin.jewel.cli;

import java.util.List;

import junit.framework.TestCase;


public class TestObjectMethods extends TestCase
{
    public interface SingleOption
    {
       @Option
       String getName();
    }

    public interface ListOption
    {
       @Option
       List<String> getName();
    }

    public interface BooleanOption
    {
       @Option
       boolean getName();
    }

    public interface OptionalOption
    {
       @Option
       String getName();

       boolean isName();
    }

    public interface UnparsedOption
    {
       @Unparsed
       List<String> getName();
    }

    public interface SeveralOptions
    {
       @Option
       String getName0();

       @Option
       List<String> getName1();

       @Option
       boolean getName2();

       @Option
       String getName3();

       boolean isName3();

       @Unparsed
       List<String> getName();
    }

    public void testHashCode() throws ArgumentValidationException
    {
       final SingleOption parsedArguments = CliFactory.parseArguments(SingleOption.class, "--name", "value");
       parsedArguments.hashCode();
    }

    public void testEquals() throws ArgumentValidationException
    {
       final SingleOption parsedArguments = CliFactory.parseArguments(SingleOption.class, "--name", "value");
       assertTrue(parsedArguments.equals(parsedArguments));
    }

    public void testNotEquals() throws ArgumentValidationException
    {
       final SingleOption parsedArguments0 = CliFactory.parseArguments(SingleOption.class, "--name", "value");
       final SingleOption parsedArguments1 = CliFactory.parseArguments(SingleOption.class, "--name", "value");
       assertFalse(parsedArguments0.equals(parsedArguments1));
    }

    public void testToString() throws ArgumentValidationException
    {
       final SingleOption parsedArguments = CliFactory.parseArguments(SingleOption.class, "--name", "value");
       assertEquals("Option values for uk.co.flamingpenguin.jewel.cli.TestObjectMethods.SingleOption: --name value", parsedArguments.toString());
    }

    public void testToStringList() throws ArgumentValidationException
    {
       final ListOption parsedArguments = CliFactory.parseArguments(ListOption.class, "--name", "value0", "value1", "value2");
       assertEquals("Option values for uk.co.flamingpenguin.jewel.cli.TestObjectMethods.ListOption: --name value0 value1 value2", parsedArguments.toString());
    }

    public void testToStringBoolean() throws ArgumentValidationException
    {
       final BooleanOption parsedArguments = CliFactory.parseArguments(BooleanOption.class, "--name", "value0", "value1", "value2");
       assertEquals("Option values for uk.co.flamingpenguin.jewel.cli.TestObjectMethods.BooleanOption: --name", parsedArguments.toString());
    }

    public void testToStringOptionalMissing() throws ArgumentValidationException
    {
       final OptionalOption parsedArguments = CliFactory.parseArguments(OptionalOption.class);
       assertEquals("Option values for uk.co.flamingpenguin.jewel.cli.TestObjectMethods.OptionalOption:", parsedArguments.toString());
    }

    public void testToStringOptionalPresent() throws ArgumentValidationException
    {
       final OptionalOption parsedArguments = CliFactory.parseArguments(OptionalOption.class, "--name", "value");
       assertEquals("Option values for uk.co.flamingpenguin.jewel.cli.TestObjectMethods.OptionalOption: --name value", parsedArguments.toString());
    }

    public void testToStringUnparsedOptions() throws ArgumentValidationException
    {
       final UnparsedOption parsedArguments = CliFactory.parseArguments(UnparsedOption.class, "value0", "value1", "value2", "value3");
       assertEquals("Option values for uk.co.flamingpenguin.jewel.cli.TestObjectMethods.UnparsedOption: value0 value1 value2 value3", parsedArguments.toString());
    }

    public void testToStringSeveralOptions() throws ArgumentValidationException
    {
       final SeveralOptions parsedArguments = CliFactory.parseArguments(SeveralOptions.class, "--name0", "value0", "--name1", "value1", "value2", "--name2", "--name3", "value3", "value4", "value5");
       assertEquals("Option values for uk.co.flamingpenguin.jewel.cli.TestObjectMethods.SeveralOptions: --name0 value0 --name1 value1 value2 --name2 --name3 value3 value4 value5", parsedArguments.toString());
    }
}