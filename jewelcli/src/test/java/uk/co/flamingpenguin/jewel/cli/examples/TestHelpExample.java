package uk.co.flamingpenguin.jewel.cli.examples;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.co.flamingpenguin.jewel.UtilitiesForTesting;
import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.Cli;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

public class TestHelpExample {
    private static final String HELP_MESSAGE =
            UtilitiesForTesting
                    .joinLines(
                            "The options available are:",
                            "\t--count value",
                            "\t--email /^[^\\S@]+@[\\w.]+$/ : your email address",
                            "\t[--help -h] : display help",
                            "\t--location value : the location of something",
                            "\t--firstLongName --secondLongName -m -n value : many aliases",
                            "\t--pattern -p value : a pattern");

    @Test public void testHelpExample() {
        final Cli<HelpExample> cli = CliFactory.createCli(HelpExample.class);

        assertEquals(HELP_MESSAGE, cli.getHelpMessage());

        try {
            cli.parseArguments("--help");
            fail("Help was requested");
        } catch (final ArgumentValidationException e) {
            assertEquals(HELP_MESSAGE, e.getMessage());
        }
    }
}
