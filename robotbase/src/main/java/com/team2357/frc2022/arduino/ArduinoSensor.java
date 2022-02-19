package com.team2357.frc2022.arduino;

import com.team2357.frc2022.Constants;
import com.team2357.lib.arduino.ArduinoUSBController;

public class ArduinoSensor extends ArduinoUSBController{


    public ArduinoSensor(String ttyDevice) {
        super(ttyDevice);
    }

    public boolean getIntakeValue() {
        boolean value = false;
        if (this.isConnected()){
            value = !this.getDeviceFieldBoolean(Constants.ARDUINO.INTAKE_SENSOR_JSON_NAME, Constants.ARDUINO.INTAKE_SENSOR_STATE_FIELD);
        }
        
        return value;
    }

    public boolean getFeederValue() {
        boolean value = false;
        if (this.isConnected()){
            value = !this.getDeviceFieldBoolean(Constants.ARDUINO.FEEDER_SENSOR_JSON_NAME, Constants.ARDUINO.FEEDER_SENSOR_STATE_FIELD);
        }
        
        return value;
    }

    public boolean getTurretValue() {
        boolean value = false;
        if (this.isConnected()){
            value = !this.getDeviceFieldBoolean(Constants.ARDUINO.TURRET_SENSOR_JSON_NAME, Constants.ARDUINO.TURRET_SENSOR_STATE_FIELD);
        }
        return value;
    }
    
}
