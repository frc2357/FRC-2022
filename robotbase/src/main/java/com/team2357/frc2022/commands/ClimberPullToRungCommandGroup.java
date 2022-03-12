package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberPullToRungCommandGroup extends SequentialCommandGroup{
    private ClimberSubsystem m_climbSub;

    public ClimberPullToRungCommandGroup(ClimberSubsystem climbSub) {
        m_climbSub = climbSub;

        addCommands(new ClimberSetPivotCommand(climbSub, true));

        addCommands(new ParallelCommandGroup(new ClimberReleaseLatchOnStrainCommand(m_climbSub), 
                                            new ClimberGoToRotationsCommand(m_climbSub, Constants.CLIMBER.PULL_ONTO_RUNG_ROTATIONS)));

        addRequirements(m_climbSub);
    }


}
