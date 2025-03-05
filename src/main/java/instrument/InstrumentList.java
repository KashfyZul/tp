package instrument;

import java.util.ArrayList;
import ui.Ui;
import exceptions.emptyDescriptionException;

public class InstrumentList {
    private ArrayList<Instrument> instruments;
    private int numberOfInstruments;


    public InstrumentList() {
        this.instruments = new ArrayList<>();
        this.numberOfInstruments = 0;
    }

    public void addInstrument(String instrument) {
        if (instrument.isBlank()) { // Check if the description is empty
            throw new emptyDescriptionException("event");
        }

        try {
            switch (instrument) {
            case "Flute":
                this.instruments.add(new Flute(instrument));
                this.numberOfInstruments++;
                break;
            case "Piano":
                this.instruments.add(new Piano(instrument));
                this.numberOfInstruments++;
                break;
            case "Guitar":
                this.instruments.add(new Guitar(instrument));
                this.numberOfInstruments++;
                break;
            default:
                System.out.println("invalid instrument");
            }
        } catch (emptyDescriptionException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteInstrument(int number) {

        if (this.instruments.isEmpty()) {
            System.out.println("No instruments to delete");
            return;
        } else if (number > numberOfInstruments) {
            System.out.println("Instrument number out of bounds");
            return;
        }

        try {
            System.out.println("Deleting instrument: " + instruments.get(number - 1));
            instruments.remove(number - 1);
            numberOfInstruments--;
            System.out.println("Now you have " + numberOfInstruments + " instruments");
        } catch (Exception e) {
            System.out.println("Error in deleting instrument: " + (number));
        }
    }

    public void reserveInstrument(int number, Ui ui) {
        if (this.instruments.isEmpty()) {
            System.out.println("No instruments available for reservation");
            return;
        } else if (number > numberOfInstruments) {
            System.out.println("Instrument number out of bounds");
            return;
        }
        Instrument instToRent = instruments.get(number - 1);
        System.out.println("Would you like to reserve " + instToRent + "? [Y/N]");
        String userInput = ui.readUserInput().toUpperCase();

        if (userInput.equals("Y")) {
            instToRent.rent();
            System.out.println("Reserving instrument: " + instToRent);
        } else {
            System.out.println("Reserve cancelled");
        }
    }

    public void returnInstrument(int number, Ui ui) {
        if (this.instruments.isEmpty()) {
            System.out.println("No instruments to return");
            return;
        } else if (number > numberOfInstruments) {
            System.out.println("Instrument number out of bounds");
            return;
        }
        Instrument instToUnrent = instruments.get(number - 1);
        System.out.println("Would you like to return " + instToUnrent + "? [Y/N]");
        String userInput = ui.readUserInput().toUpperCase();

        if (userInput.equals("Y")) {
            instToUnrent.unrent();
            System.out.println("Returning instrument: " + instToUnrent);
        } else {
            System.out.println("Return cancelled");
        }
    }

    public ArrayList<Instrument> getList() {
        return this.instruments;
    }

}
