package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem.VisionTarget;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class TrackTargetCommand extends CommandLoggerBase {
    private TurretSubsystem m_turretSubsystem;
    private TogglableLimelightSubsystem m_limelightSubsystem;

    /** Target height from floor in inches */
    private double m_targetHeight = Constants.LIMELIGHT.VISION_TARGET_HEIGHT_FROM_FLOOR_INCHES;
    private boolean m_finishWhenLocked;

    public TrackTargetCommand(TurretSubsystem turretSubsystem, TogglableLimelightSubsystem limelightSubsystem) {
        this(turretSubsystem, limelightSubsystem, false);
    }

    /**
     * This command uses the limelight to track targets. Uses {@link Constants} to
     * find the target.
     * 
     * @param turretSubsystem    The {@link TurretSubsystem}.
     * @param limelightSubsystem The {@link LimelightSubsystem}.
     */
    public TrackTargetCommand(TurretSubsystem turretSubsystem, TogglableLimelightSubsystem limelightSubsystem,
            boolean finishWhenLocked) {
        m_turretSubsystem = turretSubsystem;
        m_limelightSubsystem = limelightSubsystem;
        m_finishWhenLocked = finishWhenLocked;

        if(!m_turretSubsystem.isClosedLoopEnabled() || !m_limelightSubsystem.isClosedLoopEnabled()){
            System.err.println("----- TrackTargetCommand canceled, closed loop is DISABLED -----");
            super.cancel();
        }
        addRequirements(m_turretSubsystem, m_limelightSubsystem);
    }

    public VisionTarget getTarget() {
        return m_limelightSubsystem.acquireTarget(m_targetHeight);
    }

    @Override
    public boolean isFinished() {
        if (m_finishWhenLocked) {
            return m_turretSubsystem.isTargetLocked();
        }
        return false;
    }

    @Override
    public void initialize() {
        m_turretSubsystem.initTrackingPeriodic(this::getTarget);
    }

    @Override
    public void end(boolean cancelled) {
        m_turretSubsystem.disableTrackingPeriodic();
    }
}
