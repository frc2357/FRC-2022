package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to return climber arms to robot
 * 
 * @category Climber
 */
public class ClimberReturnCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climberSub;
    private double m_distanceMeters;
    private double m_speed;

    /**
     * 
     * @param climberSub The climber Subsystem (@link ClimberSubsystem)
     * @param speed The speed for the climber motors between 0.0 and 1.0
     * @param distanceMeters The distance the arms should travel
     */
    public ClimberReturnCommand(ClimberSubsystem climberSub, double speed, double distanceMeters) {
        m_climberSub = climberSub;
        m_speed = speed;
        m_distanceMeters = distanceMeters;
    }

    @Override
    public void initialize() {
        m_climberSub.returnClimber(m_speed);
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
