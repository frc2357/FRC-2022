package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
import com.team2357.frc2022.util.AvailableTrajectories;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoBallAutoCommandGroup extends SequentialCommandGroup {
    public TwoBallAutoCommandGroup() {
        addCommands(new FireCargoAutoCommand());
        addCommands(new ParallelCommandGroup(
                new IntakeDeployCommand(),
                AvailableTrajectories.leaveTarmacTrajectory));
        addCommands(new FireCargoAutoCommand());
    }
}
