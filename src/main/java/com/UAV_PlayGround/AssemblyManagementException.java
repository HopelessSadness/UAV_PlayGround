package com.UAV_PlayGround;

public class AssemblyManagementException extends Exception{
    public AssemblyManagementException(String message) {
        super(String.format("com.UAV_PlayGround: %s", message));
    }
}
