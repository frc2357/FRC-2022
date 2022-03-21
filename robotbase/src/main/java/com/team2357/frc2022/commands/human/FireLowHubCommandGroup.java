package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.feeder.FeederShootCommand;
import com.team2357.frc2022.commands.shooter.ShootLowHubCommand;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class FireLowHubCommandGroup extends ParallelCommandGroup {
    public FireLowHubCommandGroup() {
        addCommands(new ShootLowHubCommand());
        addCommands(new FeederShootCommand());
    }
}
