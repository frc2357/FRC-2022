package com.team2357.lib.arduino;

public class ArduinoSensor extends ArduinoUSBController{

    private String m_deviceName;

    public ArduinoSensor(String ttyDevice, String deviceName) {
        super(ttyDevice);
        
        m_deviceName = deviceName;
    }

    public boolean getIntakeValue() {
        boolean value = false;
        if (this.isConnected()){
            value = !this.getDeviceFieldBoolean(m_deviceName, "state");
        }
        
        return value;
    }

    public boolean getFeederValue() {
        boolean value = false;
        if (this.isConnected()){
            value = !this.getDeviceFieldBoolean(m_deviceName, "state");
        }
        
        return value;
    }

    public boolean getTurretValue() {
        boolean value = false;
        if (this.isConnected()){
            value = !this.getDeviceFieldBoolean(m_deviceName, "state");
        }
        return value;
    }
    
}
