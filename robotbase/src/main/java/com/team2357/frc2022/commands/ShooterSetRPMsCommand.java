package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Set the shooter to specific rpm {@link ShooterSubsystem}.
 * 
 * @category Shooter
 */
public class ShooterSetRPMsCommand extends CommandLoggerBase {
    private ShooterSubsystem m_shooterSub;
    private double m_bottomRpms;
    private double m_topRpms;

    /**
     * 
     * @param shooterSub The shooter subsystem {@link ShooterSubsystem}
     * @param bottomRpms Bottom motors RPM
     * @param topRpms    Top Motor RPM
     */
    public ShooterSetRPMsCommand(ShooterSubsystem shooterSub, double bottomRpms, double topRpms) {
        m_shooterSub = shooterSub;
        m_bottomRpms = bottomRpms;
        m_topRpms = topRpms;
        addRequirements(m_shooterSub);
    }

    @Override
    public void initialize() {
        m_shooterSub.setRPMTop(m_topRpms);
        m_shooterSub.setRPMBottom(m_bottomRpms);
    }

    @Override
    public void end(boolean isInterrupted) {
        m_shooterSub.setRPMTop(0);
        m_shooterSub.setRPMBottom(0);
    }
}
