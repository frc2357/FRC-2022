package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberExtendToRungCommandGroup extends SequentialCommandGroup{
    public ClimberExtendToRungCommandGroup(ClimberSubsystem climbSub) {
        addCommands(new ClimberSetPivotCommand(climbSub, false));
        addCommands(new ClimberGoToRotationsCommand(climbSub, Constants.CLIMBER.EXTEND_TO_RUNG_ROTATIONS));

        addRequirements(climbSub);
    }
}
