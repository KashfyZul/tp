package commands;

import instrument.InstrumentList;
import parser.Parser;
import ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.UserList;
import user.UserUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DefaultCommandTest {
    private DefaultCommand defaultCommand;
    private InstrumentList instrumentList;
    private Ui ui;
    private Parser parser;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private UserUtils userUtils;
    private UserList userList;

    @BeforeEach
    void setUp() {
        defaultCommand = new DefaultCommand();
        instrumentList = new InstrumentList();
        ui = new Ui();
        parser = new Parser();
        userList = new UserList(ui);
        userUtils = new UserUtils(ui, userList);

        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testExecutePrintsErrorMessage() {
        // Execute the command
        defaultCommand.execute(instrumentList, ui, userUtils);

        // Capture output and check if the correct error message is printed
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("No matching command found"),
                "DefaultCommand should print an error message for unknown commands.");
    }

    @Test
    void testIsExitReturnsFalse() {
        assertFalse(defaultCommand.isExit(), "DefaultCommand should not trigger exit.");
    }
}
