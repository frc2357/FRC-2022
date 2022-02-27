package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IntakeDeployAndDeactivateCommandGroup extends SequentialCommandGroup{
    private IntakeSubsystem m_intakeSub;

    public IntakeDeployAndDeactivateCommandGroup(IntakeSubsystem intakeSub) {
        m_intakeSub = intakeSub;

        addCommands(new IntakeDeployCommandGroup(m_intakeSub), new IntakeDeactivateCommandGroup(m_intakeSub));

        addRequirements(m_intakeSub);
    }
}
