package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootAgainstHubCommand extends ParallelRaceGroup{
    public ShootAgainstHubCommand(FeederSubsystem feederSub, ShooterSubsystem shooterSub){
        addCommands(new ShooterSetRPMsCommand(shooterSub, Constants.SHOOTER.SHOOT_AGAINST_HUB.SHOOTER_SPEED_TOP, Constants.SHOOTER.SHOOT_AGAINST_HUB.SHOOTER_SPEED_BOTTOM));
        addCommands(new WaitCommand(Constants.SHOOTER.SHOOT_AGAINST_HUB.WAIT_AFTER_SHOOTER));
        addCommands(new FeederSetSpeedCommand(feederSub, Constants.SHOOTER.SHOOT_AGAINST_HUB.FEEDER_SPEED));
        
        addRequirements(feederSub, shooterSub);
    }
}
