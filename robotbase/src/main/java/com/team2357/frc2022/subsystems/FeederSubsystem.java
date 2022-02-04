package com.team2357.frc2022.subsystems;

import com.team2357.frc2022.Constants;
import com.team2357.lib.arduino.ArduinoUSBController;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class FeederSubsystem extends ClosedLoopSubsystem {
private WPI_TalonSRX m_feederMotor;
private ArduinoUSBController m_arduinoIRSensor;


public FeederSubsystem(WPI_TalonSRX talonSRX) {
    m_feederMotor = talonSRX;
    m_feederMotor.setInverted(true); //Does this still need to be inverted?
    m_arduinoIRSensor = new ArduinoUSBController(Constants.ARDUINO.FEEDER_IR_SENSOR_DEVICE_NAME);

    m_arduinoIRSensor.start();

    addChild("feederMotor", m_feederMotor);
}

public void runFeedermotor(double speed) {
    m_feederMotor.set(ControlMode.PercentOutput,speed);
}

@Override
public void periodic() {
    System.out.println(isBallAtFeederWheel());
}

//Sensor state will return false when an object is too close. Function will flip that to true for reability
public boolean isBallAtFeederWheel(){
    boolean isSensorBlocked = false;
    if(m_arduinoIRSensor.isConnected())
    {
        isSensorBlocked = !m_arduinoIRSensor.getDeviceFieldBoolean(Constants.ARDUINO.FEEDER_IR_SENSOR_DEVICE_NAME, "state");
    }
    return isSensorBlocked;
}

}



