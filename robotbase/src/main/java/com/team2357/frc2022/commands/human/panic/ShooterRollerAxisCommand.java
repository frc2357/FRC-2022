package com.team2357.frc2022.commands.human.panic;

import com.team2357.frc2022.controls.AxisInterface;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ShooterRollerAxisCommand extends CommandLoggerBase {
    private AxisInterface m_axis;

    public ShooterRollerAxisCommand(AxisInterface axis) {
        m_axis = axis;
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void execute() {
        double axisValue = m_axis.getValue();
        ShooterSubsystem.getInstance().setShooterMotorsAxisSpeed(axisValue);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
