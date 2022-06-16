package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberExtendToRungCommandGroup extends SequentialCommandGroup{
    public ClimberExtendToRungCommandGroup() {
        addCommands(new ClimberSetUprightCommand(false));
        addCommands(new ClimberGoToRotationsCommand(Constants.CLIMBER.EXTEND_TO_RUNG_ROTATIONS));

        addRequirements(ClimberSubsystem.getInstance());
    }
}
