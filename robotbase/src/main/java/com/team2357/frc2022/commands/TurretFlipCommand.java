package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.TurretSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TurretFlipCommand extends SequentialCommandGroup {
    public TurretFlipCommand (TurretSubsystem turretSub) {
        addCommands(new TurretSetRotationCommand(turretSub, turretSub.calculateTurretFlipRotations()));
    }
}
