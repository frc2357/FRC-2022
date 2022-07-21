package com.team2357.frc2022.commands.shooter;

import com.team2357.frc2022.commands.feeder.FeederShootCommand;
import com.team2357.frc2022.commands.shooter.ShootTaxiLineCommand;
import com.team2357.frc2022.commands.shooter.ShooterWaitForRPMsCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootOverBotCommandGroup extends ParallelCommandGroup{
    public ShootOverBotCommandGroup () {

        addCommands(new ShooterSetRPMsCommand(650, 3200));
        addCommands(new SequentialCommandGroup(new WaitCommand(0.25), new FeederShootCommand()));
        addRequirements(ShooterSubsystem.getInstance(), FeederSubsystem.getInstance());
    }
}
