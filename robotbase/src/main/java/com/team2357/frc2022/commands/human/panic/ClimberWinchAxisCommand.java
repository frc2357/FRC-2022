package com.team2357.frc2022.commands.human.panic;

import com.team2357.frc2022.controls.AxisInterface;
import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberWinchAxisCommand extends CommandLoggerBase {
    private AxisInterface m_axis;

    public ClimberWinchAxisCommand(AxisInterface axis) {
        m_axis = axis;
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void execute() {
        ClimberSubsystem climber = ClimberSubsystem.getInstance();
        double axisValue = m_axis.getValue();

        System.out.println("ClimberWinchAxisCommand:" + axisValue);
        //climber.climberMotorSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}