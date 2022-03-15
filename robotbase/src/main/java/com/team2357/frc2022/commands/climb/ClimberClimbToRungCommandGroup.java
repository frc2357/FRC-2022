package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberClimbToRungCommandGroup extends SequentialCommandGroup {
    public ClimberClimbToRungCommandGroup() {
        addCommands(new ClimberExtendToRungCommandGroup());

        addCommands(new ClimberPullToRungCommandGroup());

        addRequirements(ClimberSubsystem.getInstance());
    }
}
