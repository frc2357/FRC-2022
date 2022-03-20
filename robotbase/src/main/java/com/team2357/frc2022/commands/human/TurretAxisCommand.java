package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.controls.AxisInterface;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TurretAxisCommand extends CommandLoggerBase {
    private AxisInterface m_axis;

    public TurretAxisCommand(AxisInterface axis) {
        m_axis = axis;
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void execute() {
        double axisValue = m_axis.getValue();
        TurretSubsystem.getInstance().setTurretAxisSpeed(axisValue);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().stop();
    }
}
