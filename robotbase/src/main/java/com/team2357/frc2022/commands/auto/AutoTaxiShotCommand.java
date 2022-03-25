package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.commands.feeder.FeederShootCommand;
import com.team2357.frc2022.commands.shooter.ShootTaxiLineCommand;
import com.team2357.frc2022.commands.shooter.ShooterWaitForRPMsCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoTaxiShotCommand extends ParallelCommandGroup {
    double m_time;

    public AutoTaxiShotCommand() {
        addCommands(new ShootTaxiLineCommand());
        addCommands(new SequentialCommandGroup(new ShooterWaitForRPMsCommand(), new FeederShootCommand()));
        addRequirements(ShooterSubsystem.getInstance(), FeederSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        super.initialize();
        m_time = System.currentTimeMillis() + 2000;
    }

    @Override 
   public boolean isFinished() {
        return super.isFinished() || (SensorSubsystem.getInstance().isRobotEmpty() && m_time < System.currentTimeMillis());
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        ShooterSubsystem.getInstance().stop();
    }
    
}
