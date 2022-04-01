package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.commands.human.panic.ClimberWinchResetCommand;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberPullToRungCommandGroup extends SequentialCommandGroup {
    public ClimberPullToRungCommandGroup() {
        addCommands(new ClimberSetLatchCommand(true));

        addCommands(new ParallelDeadlineGroup(new ClimberPulledUpCommand(),
                new SequentialCommandGroup(
                        new ClimberGoToRotationsCommand(Constants.CLIMBER.PULL_ONTO_RUNG_ROTATIONS),
                        new ClimberProportionalCommand(Constants.CLIMBER.SNUG_TO_BAR_SPEED))));

        addCommands(new ClimberSetLatchCommand(false));

        addCommands(new ClimberWinchResetCommand());

        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        super.initialize();
        ClimberSubsystem.getInstance().switchToLoadedClimberPID();
        System.out.println("Pull to rung");

    }
}