package com.team2357.frc2022.commands;

import javax.xml.namespace.QName;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Command to set the pivot of the pivoting arms
 * 
 * @category Climber
 */
public class ClimberSetPivotCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climbSub;
    private DoubleSolenoid.Value m_value;

    ClimberSetPivotCommand(ClimberSubsystem climbSub, DoubleSolenoid.Value value) {
        m_value = value;
        m_climbSub = climbSub;
        addRequirements(m_climbSub);
    }

    @Override
    public void initialize() {
        m_climbSub.setClimberPivot(m_value);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
