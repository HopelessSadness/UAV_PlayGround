package com.UAV_PlayGround;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AssemblyManagementException extends Exception{
    public AssemblyManagementException(String message, Logger logger) {
        super(String.format("com.UAV_PlayGround: %s", message));
        logger.log(Level.SEVERE, message);
    }
}
