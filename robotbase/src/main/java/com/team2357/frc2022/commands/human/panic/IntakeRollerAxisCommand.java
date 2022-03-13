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
        IntakeRollerSubsystem intakeRoller = IntakeRollerSubsystem.getInstance();
        double axisValue = m_axis.getValue();

        System.out.println("IntakeRollerAxisCommand:" + axisValue);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
