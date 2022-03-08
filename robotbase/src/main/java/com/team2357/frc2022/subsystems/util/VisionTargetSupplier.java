package com.team2357.frc2022.subsystems.util;

import com.team2357.lib.subsystems.LimelightSubsystem.VisionTarget;

// TODO: Consider making this more general (not just vision) or including it in the LimelightSubsystem.
@FunctionalInterface
public abstract interface VisionTargetSupplier {
    public abstract VisionTarget getAsVisionTarget();
}