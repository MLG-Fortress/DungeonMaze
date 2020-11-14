package com.timvisee.dungeonmaze;

import com.timvisee.dungeonmaze.command.CommandHandler;
import com.timvisee.dungeonmaze.command.CommandHandlerService;
import com.timvisee.dungeonmaze.config.ConfigHandler;
import com.timvisee.dungeonmaze.logger.DungeonMazeLogger;
import com.timvisee.dungeonmaze.logger.LoggerManager;
import com.timvisee.dungeonmaze.service.*;
import com.timvisee.dungeonmaze.config.ConfigHandlerService;
import com.timvisee.dungeonmaze.structure.CustomStructureManagerService;
import com.timvisee.dungeonmaze.logger.LoggerService;
import com.timvisee.dungeonmaze.update.UpdateChecker;
import com.timvisee.dungeonmaze.update.UpdateCheckerService;
import com.timvisee.dungeonmaze.world.WorldManagerService;
import com.timvisee.dungeonmaze.structure.CustomStructureManager;
import com.timvisee.dungeonmaze.world.WorldManager;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonRegionGridManager;
import com.timvisee.dungeonmaze.world.dungeon.chunk.grid.DungeonChunkGridManagerService;

import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
public class Core {

    // TODO: Do we need to keep a non-static version of each method available?

    /**
     * Static Core instance.
     */
    public static Core instance;

    /**
     * Defines whether the core has been initialized.
     */
    private boolean init = false;

    /**
     * Service manager instance.
     */
    private ServiceManager serviceManager;

    /**
     * Logger service instance.
     */
    private LoggerService loggerService = new LoggerService();

    /**
     * Config handler service instance.
     */
    private ConfigHandlerService configHandlerService = new ConfigHandlerService();

    /**
     * Command handler service instance.
     */
    private CommandHandlerService commandHandlerService = new CommandHandlerService();

    /**
     * World manager service instance.
     */
    private WorldManagerService worldManagerService = new WorldManagerService();

    /**
     * Dungeon chunk grid manager service instance.
     */
    private DungeonChunkGridManagerService dungeonChunkGridManagerService = new DungeonChunkGridManagerService();

    /**
     * Update checker service instance.
     */
    private UpdateCheckerService updateCheckerService = new UpdateCheckerService();

    /**
     * Custom structure manager service instance.
     */
    private CustomStructureManagerService customStructureManagerService = new CustomStructureManagerService();

    /**
     * Defines the initialization time of the core.
     */
    private Date initTime = new Date();

    /**
     * Constructor.
     *
     * @param init True to immediately initialize.
     */
    public Core(boolean init) {
        // Set the static instance
        Core.instance = this;

        // Initialize
        if(init)
            this.init();
    }

    /**
     * Initialize the core.
     * This method will initialize and set up all handlers and managers used by the core.
     *
     * @return True on success, false on failure.
     */
    public boolean init() {
        // Make sure the Core hasn't been instantiated already
        if(this.isInit())
            return true;

        // Set the initialization time
        this.initTime = new Date();

        // Initialize the service manager
        this.serviceManager = new ServiceManager();

        // Register all services
        this.serviceManager.unregisterAllServices();
        this.serviceManager.registerService(this.loggerService);
        this.serviceManager.registerService(this.configHandlerService);
        this.serviceManager.registerService(this.commandHandlerService);
        this.serviceManager.registerService(this.worldManagerService);
        this.serviceManager.registerService(this.dungeonChunkGridManagerService);
//        this.serviceManager.registerService(this.updateCheckerService);
        this.serviceManager.registerService(this.customStructureManagerService);

        // Initialize all services
        if(!this.serviceManager.initServices())
            return false;

        // Everything has been set up successfully, return the result
        this.init = true;
        return true;
    }

    /**
     * Destroy all services.
     *
     * @param force True to force to destroy all services, even if one service couldn't be destroyed. If force mode is
     *              used all service states will be set to destroyed even if the destruction failed.
     *
     * @return True on success, false on failure.
     */
    public boolean destroy(boolean force) {
        // Make sure the core is initialized, or the method must be forced
        if(!this.isInit() && !force)
            return true;

        // Destroy all services
        if(!this.serviceManager.destroyServices(force))
            if(!force)
                return false;

        // Return the result
        this.init = false;
        return true;
    }

    /**
     * Check whether the Core has been initialized.
     *
     * @return True if the core has been initialized, false otherwise.
     */
    public boolean isInit() {
        return this.init;
    }

    /**
     * Get the service manager.
     *
     * @return Service manager instance.
     */
    public ServiceManager getServiceManager() {
        return this.serviceManager;
    }

    /**
     * Get the logger manager.
     *
     * @return Logger manager instance.
     */
    public static LoggerManager getLoggerManager() {
        return Core.instance._getLoggerManager();
    }

    /**
     * Get the logger manager.
     *
     * @return Logger manager instance.
     */
    public LoggerManager _getLoggerManager() {
        return this.loggerService.getLoggerManager();
    }

    /**
     * Get the Dungeon Maze logger.
     *
     * @return Dungeon Maze logger instance.
     */
    public static DungeonMazeLogger getLogger() {
        return Core.instance._getLogger();
    }
    
    /**
     * Get the Dungeon Maze logger.
     *
     * @return Dungeon Maze logger instance.
     */
    public DungeonMazeLogger _getLogger() {
        /// Make sure the logger service is initialized
        if(this.loggerService == null)
            return new DungeonMazeLogger(DungeonMaze.instance.getLogger());

        // Get and return the logger
        return this.loggerService.getLogger();
    }

    /**
     * Get the config handler.
     *
     * @return Config handler instance.
     */
    public static ConfigHandler getConfigHandler() {
        return Core.instance._getConfigHandler();
    }

    /**
     * Get the config handler.
     *
     * @return Config handler instance.
     */
    public ConfigHandler _getConfigHandler() {
        return this.configHandlerService.getConfigHandler();
    }

    /**
     * Get the command handler.
     *
     * @return Command handler instance.
     */
    public static CommandHandler getCommandHandler() {
        return Core.instance._getCommandHandler();
    }

    /**
     * Get the command handler.
     *
     * @return Command handler instance.
     */
    public CommandHandler _getCommandHandler() {
        return this.commandHandlerService.getCommandHandler();
    }

    /**
     * Get the world manager.
     *
     * @return World manager instance.
     */
    public static WorldManager getWorldManager() {
        return Core.instance._getWorldManager();
    }

    /**
     * Get the world manager.
     *
     * @return World manager instance.
     */
    public WorldManager _getWorldManager() {
        return this.worldManagerService.getWorldManager();
    }

    /**
     * Get the dungeon region grid manager.
     *
     * @return Dungeon region grid manager instance.
     */
    public static DungeonRegionGridManager getDungeonRegionGridManager() {
        return Core.instance._getDungeonRegionGridManager();
    }

    /**
     * Get the dungeon region grid manager.
     *
     * @return Dungeon region grid manager instance.
     */
    public DungeonRegionGridManager _getDungeonRegionGridManager() {
        return this.dungeonChunkGridManagerService.getDungeonRegionGridManager();
    }

    /**
     * Get the update checker.
     *
     * @return Update checker instance.
     */
    public static UpdateChecker getUpdateChecker() {
        return Core.instance._getUpdateChecker();
    }

    /**
     * Get the update checker.
     *
     * @return Update checker instance.
     */
    public UpdateChecker _getUpdateChecker() {
        return this.updateCheckerService.getUpdateChecker();
    }

    /**
     * Get the update checker service, if started.
     *
     * @return Update checker service instance, or null.
     */
    public static UpdateCheckerService getUpdateCheckerService() {
        return Core.instance._getUpdateCheckerService();
    }

    /**
     * Get the update checker service, if started.
     *
     * @return Update checker service instance, or null.
     */
    public UpdateCheckerService _getUpdateCheckerService() {
        return this.updateCheckerService;
    }

    /**
     * Get the custom structure manager.
     *
     * @return Custom structure manager instance.
     */
    public static CustomStructureManager getCustomStructureManager() {
        return Core.instance._getCustomStructureManager();
    }

    /**
     * Get the custom structure manager.
     *
     * @return Custom structure manager instance.
     */
    public CustomStructureManager _getCustomStructureManager() {
        return this.customStructureManagerService.getCustomStructureManager();
    }

    /**
     * Get the initialization time of the Core.
     *
     * @return Core initialization time.
     */
    public static Date getInitializationTime() {
        return Core.instance._getInitializationTime();
    }

    /**
     * Get the initialization time of the Core.
     *
     * @return Core initialization time.
     */
    public Date _getInitializationTime() {
        return this.initTime;
    }
}
