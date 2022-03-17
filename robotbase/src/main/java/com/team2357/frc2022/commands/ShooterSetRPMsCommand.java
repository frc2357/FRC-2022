package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Set the shooter to specific rpm {@link ShooterSubsystem}.
 * 
 * @category Shooter
 */
public class ShooterSetRPMsCommand extends CommandLoggerBase {
    private double m_bottomRpms;
    private double m_topRpms;

    /**
     * 
     * @param shooterSub The shooter subsystem {@link ShooterSubsystem}
     * @param bottomRpms Bottom motors RPM
     * @param topRpms    Top Motor RPM
     */
    public ShooterSetRPMsCommand(double bottomRpms, double topRpms) {
        m_bottomRpms = bottomRpms;
        m_topRpms = topRpms;
        addRequirements(ShooterSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ShooterSubsystem.getInstance().setRPMTop(m_topRpms);
        ShooterSubsystem.getInstance().setRPMBottom(m_bottomRpms);
    }

    @Override
    public void end(boolean isInterrupted) {
        ShooterSubsystem.getInstance().setRPMTop(0);
        ShooterSubsystem.getInstance().setRPMBottom(0);
    }
}
