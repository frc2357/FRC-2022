package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.feeder.FeederShootCommand;
import com.team2357.frc2022.commands.shooter.ShootTaxiLineCommand;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class FireTaxiLineCommandGroup extends ParallelCommandGroup{
    public FireTaxiLineCommandGroup () {
        addCommands(new ShootTaxiLineCommand());
        addCommands(new FeederShootCommand());
    }
}
