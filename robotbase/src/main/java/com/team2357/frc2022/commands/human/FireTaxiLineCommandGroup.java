package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.feeder.FeederShootCommand;
import com.team2357.frc2022.commands.shooter.ShootTaxiLineCommand;
import com.team2357.frc2022.commands.shooter.ShooterWaitForRPMsCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FireTaxiLineCommandGroup extends ParallelCommandGroup{
    public FireTaxiLineCommandGroup () {
        addCommands(new ShootTaxiLineCommand());
        addCommands(new SequentialCommandGroup(new ShooterWaitForRPMsCommand(), new FeederShootCommand()));
        addRequirements(ShooterSubsystem.getInstance(), FeederSubsystem.getInstance());
    }
}
