package com.team2357.frc2022.arduino;

import com.team2357.frc2022.Constants;
import com.team2357.lib.arduino.ArduinoUSBController;

public class RobotArduino extends ArduinoUSBController{


    public RobotArduino(String ttyDevice) {
        super(ttyDevice);
        super.start();
    }

    public boolean getIntakeValue() {
        boolean value = false;
        System.out.println(super.isConnected());

        if (super.isConnected()){
            value = !super.getDeviceFieldBoolean(Constants.ARDUINO.INTAKE_SENSOR_JSON_NAME, Constants.ARDUINO.INTAKE_SENSOR_STATE_FIELD);
        }
        
        return value;
    }

    public boolean getFeederValue() {
        boolean value = false;
        if (super.isConnected()){
            value = !super.getDeviceFieldBoolean(Constants.ARDUINO.FEEDER_SENSOR_JSON_NAME, Constants.ARDUINO.FEEDER_SENSOR_STATE_FIELD);
        }
        
        return value;
    }

    public boolean getTurretValue() {
        boolean value = false;
        if (super.isConnected()){
            value = !super.getDeviceFieldBoolean(Constants.ARDUINO.TURRET_SENSOR_JSON_NAME, Constants.ARDUINO.TURRET_SENSOR_STATE_FIELD);
        }
        return value;
    }
    
}
