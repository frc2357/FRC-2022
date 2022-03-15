package com.team2357.frc2022.commands.human.panic;

import com.team2357.frc2022.controls.AxisInterface;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeRollerAxisCommand extends CommandLoggerBase {
    private AxisInterface m_axis;

    public IntakeRollerAxisCommand(AxisInterface axis) {
        m_axis = axis;
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override
    public void execute() {
        double axisValue = m_axis.getValue();
        IntakeRollerSubsystem.getInstance().setAxisRollerSpeed(axisValue);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
    @Override
    public void end(boolean interrupted) {
        IntakeRollerSubsystem.getInstance().stop();
    }
}
