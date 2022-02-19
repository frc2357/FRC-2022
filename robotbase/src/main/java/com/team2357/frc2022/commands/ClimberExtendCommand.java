package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to raise the climber arms
 * 
 * @category Climber
 */
public class ClimberExtendCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climberSub;
    private double m_climberMotorRotations;
    private double m_speed;
    private boolean m_isFinished;

    /**
     * 
     * @param climbSub              The climber Subsystem (@link ClimberSubsystem)
     * @param speed                 The speed for the climber motors between 0.0
     *                              and 1.0
     * @param climberMotorRotations How many spindle rotaions the climber will make
     */
    public ClimberExtendCommand(ClimberSubsystem climbSub, double speed, double climberMotorRotations) {
        m_climberSub = climbSub;
        m_speed = speed;
        m_climberMotorRotations = climberMotorRotations;
        m_isFinished = false;
        addRequirements(climbSub);
    }

    @Override
    public void initialize() {
        m_climberSub.extendClimber(m_speed);
    }

    @Override
    public void execute() {
        m_isFinished = m_climberSub.validate(m_climberMotorRotations);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}