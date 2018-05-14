package server.terminal.commands;

import server.terminal.TerminalCommands;
import server.terminal.TerminalCommand;

public class StopServer extends TerminalCommand {

	public StopServer(TerminalCommands terminal) {
		super(terminal);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Object... elist) {
		getObject().getServer().goOffline();	
	}
}

