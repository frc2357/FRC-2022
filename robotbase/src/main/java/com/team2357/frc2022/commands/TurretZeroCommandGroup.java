package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.TurretSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TurretZeroCommandGroup extends SequentialCommandGroup {
    TurretSubsystem m_turretSub;
    boolean m_isZeroed;

    TurretZeroCommandGroup(TurretSubsystem turretSub) {
        m_turretSub = turretSub;

        super.addCommands(
                new TurretRotateDurationCommand(m_turretSub, Constants.TURRET.TURRET_ZERO_CLOCKWISE_COMMAND_SPEED,
                        Constants.TURRET.TURRET_ZERO_CLOCKWISE_DURATION_SECONDS),
                new TurretRotateDurationCommand(m_turretSub,
                        Constants.TURRET.TURRET_ZERO_COUNTER_CLOCKWISE_COMMAND_SPEED,
                        Constants.TURRET.TURRET_ZERO_COUNTER_CLOCKWISE_DURATION_SECONDS));

        addRequirements(m_turretSub);
    }

    @Override
    public void execute() {
        super.execute();

        m_isZeroed = m_turretSub.isOnZero();
    }

    @Override
    public boolean isFinished() {
        if (super.isFinished()) {
            System.out.println("----- Did not zero turret -----");
            return true;
        }
        return m_isZeroed;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        m_turretSub.setClosedLoopEnabled(m_isZeroed);
    }

}
