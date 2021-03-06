package com.team2357.frc2022.commands.human.panic;

import com.team2357.frc2022.controls.AxisInterface;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class FeederRollerAxisCommand extends CommandLoggerBase {
    private AxisInterface m_axis;

    public FeederRollerAxisCommand(AxisInterface axis) {
        m_axis = axis;
        addRequirements(FeederSubsystem.getInstance());
    }

    @Override
    public void execute() {
        double axisValue = m_axis.getValue();
        FeederSubsystem.getInstance().setAxisRollerSpeed(axisValue);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        FeederSubsystem.getInstance().stop();
    }
}
