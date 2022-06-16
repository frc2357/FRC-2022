package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberPullToRungCommandGroup extends SequentialCommandGroup {
    public ClimberPullToRungCommandGroup() {
        addCommands(new ClimberSetUprightCommand(true));

        addCommands(new WaitCommand(Constants.CLIMBER.CLIMBER_TIME_TO_UPRIGHT_SECONDS));

        addCommands(new ParallelCommandGroup(new ClimberReleaseLatchOnStrainCommand(Constants.CLIMBER.MOTOR_TIME_TO_EQUALIZE_AMPS_MILLIS),
                new ClimberGoToRotationsCommand(Constants.CLIMBER.PULL_ONTO_RUNG_ROTATIONS)));

        addCommands(new ClimberSetLatchCommand(false));

        addRequirements(ClimberSubsystem.getInstance());
    }
}
