package com.timvisee.dungeonmaze.command.executable;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.command.CommandParts;
import com.timvisee.dungeonmaze.command.ExecutableCommand;
import com.timvisee.dungeonmaze.util.Profiler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadPermissionsCommand extends ExecutableCommand {

    /**
     * Execute the command.
     *
     * @param sender           The command sender.
     * @param commandReference The command reference.
     * @param commandArguments The command arguments.
     *
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean executeCommand(CommandSender sender, CommandParts commandReference, CommandParts commandArguments) {
        // Profile the permissions reload process
        Profiler p = new Profiler(true);

        // Show a status message
        sender.sendMessage(ChatColor.YELLOW + "Reloading permissions...");
        Core.getLogger().info("Reloading permissions...");

        // Get the permissions manager and make sure it's valid
        if(true) {
            Core.getLogger().info("Failed to access the permissions manager after " + p.getTimeFormatted() + "!");
            sender.sendMessage(ChatColor.DARK_RED + "Failed to access the permissions manager after " + p.getTimeFormatted() + "!");
            return true;
        }
        return true;
    }
}
