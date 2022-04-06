package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    private DifferentialDrive m_differentialDrive;
    private Configuration m_config;
    public static DriveSubsystem instance;

    public static class Configuration {
        public double m_speedSensitivity;
        public double m_turnSensitivity;
    }

    public DriveSubsystem(MotorControllerGroup leftControllers, MotorControllerGroup rightControllers) {
        rightControllers.setInverted(true);
        m_differentialDrive = new DifferentialDrive(leftControllers, rightControllers);
        instance = this;
    }

    public void config(Configuration configuration) {
        m_config = configuration;
    }

    public void setDrive(double speed, double turn) {
        System.out.println("Speed: " + speed);
        System.out.println("Turn: " + turn);
        speed *= m_config.m_speedSensitivity;
        turn *= m_config.m_turnSensitivity;
        m_differentialDrive.arcadeDrive(speed, turn);
    }

    public static DriveSubsystem getInstance() {
        return instance;
    }

}
