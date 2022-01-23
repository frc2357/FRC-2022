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
  private double m_climberExtensionMeters;
    private double m_speed;
    private boolean m_isFinished;

    /**
     * 
     * @param climbSub The climber Subsystem (@link ClimberSubsystem)
     * @param speed The speed for the climber motors between 0.0 and 1.0
     * @param climberExtensionMeters How far the climber is extended
     */
    public ClimberExtendCommand(ClimberSubsystem climbSub, double speed, double climberExtensionMeters) {
        m_climberSub = climbSub;
        m_speed = speed;
        m_climberExtensionMeters = climberExtensionMeters;
        m_isFinished = false;
        addRequirements(climbSub);
    }

    @Override
    public void initialize() {
        m_climberSub.returnClimber(m_speed);
    }

    @Override
    public void execute() {
        m_isFinished = m_climberSub.validateExtensionDistance(m_climberExtensionMeters);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
