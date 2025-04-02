package commands;

import instrument.InstrumentList;
import ui.Ui;
import user.UserUtils;

public class DeleteCommand extends Command {
    public DeleteCommand(String command) {
        super(command);
    }

    @Override
    public void execute(InstrumentList instrumentList, Ui ui, UserUtils userUtils) {
        instrumentList.deleteInstrument(Integer.parseInt(this.name));
        ui.printInstrumentList(instrumentList.getList());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
