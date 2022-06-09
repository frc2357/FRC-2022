package com.team2357.frc2022.commands;

import com.team2357.frc2022.commands.feeder.FeederAdvanceCommand;
import com.team2357.frc2022.commands.feeder.FeederExtraAdvanceCommand;
import com.team2357.frc2022.commands.feeder.FeederPackCommand;
import com.team2357.frc2022.commands.intake.IntakeAdvanceCommand;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CargoAdjustCommand extends SequentialCommandGroup {
    public CargoAdjustCommand() {
        // First advance the feeder and intake
        addCommands(new ParallelDeadlineGroup(
            new WaitCommand(0.5),
            new FeederAdvanceCommand(),
            new IntakeAdvanceCommand()
        ));

        // Then pack the feeder down into the intake
        addCommands(new ParallelDeadlineGroup(
            new WaitCommand(0.25),
            new FeederPackCommand()
        ));
        addCommands(new FeederExtraAdvanceCommand());

        // Now advance just the feeder
        addCommands(new ParallelDeadlineGroup(
            new WaitCommand(0.25),
            new FeederAdvanceCommand()
        ));

        // And pop it up just a little more to be stable
        addCommands(new FeederExtraAdvanceCommand());

        // Then advance the intake behind it
        addCommands(new ParallelDeadlineGroup(
            new WaitCommand(0.25),
            new IntakeAdvanceCommand())
        );
    }
}
