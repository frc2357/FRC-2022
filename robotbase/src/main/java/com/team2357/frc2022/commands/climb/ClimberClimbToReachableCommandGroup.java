package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * Command to climb to a reachable rung from the floor (low or mid rung)
 */
public class ClimberClimbToReachableCommandGroup extends SequentialCommandGroup{
    public ClimberClimbToReachableCommandGroup() {
       // addCommands(new ClimberGoToRotationsCommand(Constants.CLIMBER.EXTEND_TO_RUNG_ROTATIONS));
        //addCommands(new ClimberSetLatchCommand(true));
System.out.println("Climbing to reachable");
        addRequirements(ClimberSubsystem.getInstance());
    }
}
