package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberClimbToHighCommandGroup extends SequentialCommandGroup{
    public ClimberClimbToHighCommandGroup(ClimberSubsystem climbSub) {
        addCommands(new ClimberClimbToReachableCommandGroup(climbSub));
        addCommands(new WaitCommand(Constants.CLIMBER.CLIMBER_DELAY_BETWEEN_RUNGS_SECONDS));
        addCommands(new ClimberClimbToRungCommandGroup(climbSub));

        addRequirements(climbSub);
    }
}
