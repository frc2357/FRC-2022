package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberClimbToRungCommandGroup extends SequentialCommandGroup {
    public ClimberClimbToRungCommandGroup(ClimberSubsystem climbSub) {
        addCommands(new ClimberExtendToRungCommandGroup(climbSub));

        addCommands(new ClimberPullToRungCommandGroup(climbSub));

        addRequirements(climbSub);
    }
}
