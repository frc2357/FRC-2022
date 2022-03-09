package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to return climber arms to robot
 * 
 * @category Climber
 */
public class ClimberRetractCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climbSub;
    private double m_climberMotorRotations;
    private double m_speed;
    private int m_amps;

    /**
     * 
     * @param climbSub              The climber Subsystem (@link ClimberSubsystem)
     * @param speed                 The speed for the climber motors between 0.0 and
     *                              1.0
     * @param climberMotorRotations The number times the motor spindle should rotate
     */
    public ClimberRetractCommand(ClimberSubsystem climbSub, double speed, double climberMotorRotations, int amps) {
        m_climbSub = climbSub;
        m_speed = speed;
        m_climberMotorRotations = climberMotorRotations;
        m_amps = amps;

        addRequirements(m_climbSub);
    }

    @Override
    public void initialize() {
        m_climbSub.retractClimber(m_speed);
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return m_climbSub.isGripped(m_climberMotorRotations, m_amps);
    }
}
