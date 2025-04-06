package storage;

import exceptions.storage.FileCannotBeFoundException;
import exceptions.storage.FileCannotBeMadeException;
import instrument.Instrument;
import instrument.InstrumentList;
import parser.Parser;
import ui.Ui;
import commands.instrument.AddInstrumentCommand;
import user.UserUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    String outputFilePath;
    String outputText = "";
    Ui ui;
    InstrumentList instrumentList;
    File file;
    UserUtils userUtils;

    public Storage(Ui ui, UserUtils userUtils, String outputFilePath) {
        this.ui = ui;
        this.userUtils = userUtils;
        instrumentList = new InstrumentList();
        this.outputFilePath = outputFilePath;
    }

    public InstrumentList loadOldFile() throws FileCannotBeFoundException {
        validateOutputFilepath();
        try {
            loadOldEntries();
            return instrumentList;
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return instrumentList;
        }
    }

    public void saveCurrentFile(InstrumentList instrumentList) throws IOException {
        updateOutputText(instrumentList);
        writeOutputToFile();
    }

    private void updateOutputText(InstrumentList instrumentList) {
        for (Instrument instrument : instrumentList.getList()) {
            addEntryToOutputText(instrument);
        }
    }

    private void validateOutputFilepath() {
        try {
            validateFileDirectories();
            validateFile();
        } catch (IOException e) {
            throw new FileCannotBeMadeException(outputFilePath);
        }
    }

    private void validateFileDirectories() {
        if (!outputFilePath.contains("/")) {
            return;
        }
        String directoryName = Parser.parseFileDirectories(outputFilePath);
        ui.printCreatingDirectory(directoryName);
        File directory = new File(directoryName);
        if (!directory.mkdirs()) {
            ui.printDirectoryAlreadyExists();
        }
    }

    private void validateFile() throws IOException {
        file = new File(outputFilePath);
        ui.printCreatingFile(outputFilePath);
        if (!file.createNewFile()) {
            ui.printFileAlreadyExists();
        }
    }

    private void loadOldEntries() throws FileNotFoundException {
        // format: Instrument|Model|Year|LoanDate|LoanDuration|Usage|UserName
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            addEntryToSession(line);
        }
    }

    private void addEntryToSession(String line) {
        if (line.isEmpty()) {
            return;
        }
        AddInstrumentCommand c = new AddInstrumentCommand(line, true);
        c.addInstrumentToSession(instrumentList, ui, userUtils);
    }

    private void addEntryToOutputText(Instrument instrument) {
        outputText += instrument.toFileEntry() + "\n";
    }

    private void writeOutputToFile() throws IOException {
        FileWriter fileWriter = new FileWriter(outputFilePath);
        fileWriter.write(outputText);
        fileWriter.close();
    }
}
