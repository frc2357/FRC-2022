package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberSimpleRunMotorsCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climbSub;
    private double m_speed;

    public ClimberSimpleRunMotorsCommand (ClimberSubsystem climbSub, double speed) {
        m_climbSub = climbSub;
        m_speed = speed;
    }

    @Override
    public void initialize(){
        m_climbSub.climberMotorSpeed(m_speed);
    }

    @Override
    public void execute() {
       System.out.println("Left climber rotations: "+m_climbSub.getLeftClimberRotations());
       System.out.println("Right climber rotations: "+m_climbSub.getRightClimberRotations());
    }

    @Override
    public void end(boolean interrupted) {
        m_climbSub.StopClimberMotors();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
