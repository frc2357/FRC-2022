package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class TurretZeroCommand extends CommandLoggerBase {
    private TurretSubsystem m_turretSub;
    private double degrees = 0;
    private boolean m_toggleDirection = true;
    private boolean m_isFinished = false;

    public TurretZeroCommand(TurretSubsystem turretSub) {
        m_turretSub = turretSub;

        if(!m_turretSub.isClosedLoopEnabled()){
            System.err.println("----- TurretZeroCommand canceled, closed loop is DISABLED -----");
            CommandScheduler.getInstance().cancel(this);
        }

        addRequirements(m_turretSub);
    }

    @Override
    public void initialize() {
        m_turretSub.resetHeading();
    }

    @Override
    public void execute() {

        m_isFinished = m_turretSub.isOnZero();
        if (!m_isFinished && m_turretSub.atDegrees()) {

            m_turretSub.setTurretPosition(m_toggleDirection ? degrees : -1 * degrees);

            degrees += Constants.TURRET.MOTOR_DEGREES_ZERO_INCREMENT;
            m_toggleDirection = !m_toggleDirection;

        }
    }

    @Override
    public void end(boolean interrupted) {
        m_turretSub.setTurretSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}
