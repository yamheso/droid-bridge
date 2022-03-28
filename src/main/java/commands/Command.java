package commands;

import java.util.List;

public interface Command {

    default boolean isShellCommand(){
        return false;
    }

    List<String> getCommandComponents();
}