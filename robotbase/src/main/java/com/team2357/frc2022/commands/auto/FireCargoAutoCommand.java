package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.commands.feeder.FeederToShooterCommand;
import com.team2357.frc2022.commands.human.FireVisionCommand;
import com.team2357.frc2022.commands.human.TargetLockCommand;
import com.team2357.frc2022.subsystems.SensorSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FireCargoAutoCommand extends ParallelCommandGroup {
    public FireCargoAutoCommand() {

        addCommands(new FireVisionCommand());
        addCommands(new SequentialCommandGroup(
                new TargetLockCommand(),
                new FeederToShooterCommand()));
    }

    @Override
    public boolean isFinished() {
        boolean noCargo = (!SensorSubsystem.getInstance().isCargoInIndex()
                && !SensorSubsystem.getInstance().isCargoInFeeder());
        return super.isFinished() || noCargo;
    }
}
