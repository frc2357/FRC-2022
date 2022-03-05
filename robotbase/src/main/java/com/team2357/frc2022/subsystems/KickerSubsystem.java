package com.team2357.frc2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.team2357.lib.subsystems.ClosedLoopSubsystem;

public class KickerSubsystem extends ClosedLoopSubsystem{
    private CANSparkMax m_kickerMotor;

    public KickerSubsystem(CANSparkMax kickerMotor) {
        m_kickerMotor = kickerMotor;
    }

    public void runKickerMotor(double speed) {
        m_kickerMotor.set(speed);
    }
}
