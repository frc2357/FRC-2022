package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Command to climb to a reachable rung from the floor (low or mid rung)
 */
public class ClimberClimbToReachableCommandGroup extends SequentialCommandGroup{
    public ClimberClimbToReachableCommandGroup(ClimberSubsystem climbSub) {
        addCommands(new ClimberGoToRotationsCommand(climbSub, Constants.CLIMBER.EXTEND_TO_RUNG_ROTATIONS));
        addCommands(new ClimberLatchCommand(climbSub, true));

        addRequirements(climbSub);
    }
}
