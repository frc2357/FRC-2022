package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberExtendToReachableCommandGroup extends SequentialCommandGroup {

    public ClimberExtendToReachableCommandGroup() {

        addCommands(new ParallelCommandGroup(
                new ClimberSetLatchCommand(true),
                new ClimberGoToRotationsCommand(Constants.CLIMBER.EXTEND_TO_RUNG_ROTATIONS)));
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        super.initialize();
        ClimberSubsystem.getInstance().switchToUnloadedClimberPID();
        System.out.println("Extending to reachable");
    }
}
