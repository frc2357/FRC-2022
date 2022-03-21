package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.feeder.FeederToShooterCommand;
import com.team2357.frc2022.commands.shooter.ShootTaxiLineCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class FireTaxiLineCommandGroup extends ParallelCommandGroup{
    public FireTaxiLineCommandGroup () {
        addCommands(new ShootTaxiLineCommand());
        addCommands(new FeederToShooterCommand());

        addRequirements(ShooterSubsystem.getInstance(), FeederSubsystem.getInstance());
    }
}
