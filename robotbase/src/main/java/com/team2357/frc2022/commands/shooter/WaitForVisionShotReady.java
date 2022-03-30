package com.team2357.frc2022.commands.shooter;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.frc2022.subsystems.TurretSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitForVisionShotReady extends CommandBase {
    @Override
    public boolean isFinished() {
        return ShooterSubsystem.getInstance().hasTarget() && ShooterSubsystem.getInstance().isAtTargetSpeed() && TurretSubsystem.getInstance().isAtTarget();
    }
}
