package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.KickerSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class KickerSetSpeedCommand extends CommandLoggerBase{
    private KickerSubsystem m_kickerSub;
    private double m_speed;

    public KickerSetSpeedCommand(KickerSubsystem kickerSub, double speed) {
        m_kickerSub = kickerSub;
        m_speed = speed;
        addRequirements(m_kickerSub);
    }

    @Override
    public void initialize(){
        super.initialize();
        m_kickerSub.runKickerMotor(m_speed);

    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        m_kickerSub.runKickerMotor(0.0);
    }
}
