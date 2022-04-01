package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.commands.CameraLightCommand;
import com.team2357.frc2022.commands.CargoAdjustCommand;
import com.team2357.frc2022.commands.feeder.FeederAdvanceCommand;
import com.team2357.frc2022.commands.feeder.FeederExtraAdvanceCommand;
import com.team2357.frc2022.commands.shooter.ShooterReverseCommand;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IntakeDeployCommand extends CommandLoggerBase {
    private Command m_cameraOn;
    private Command m_deploy;
    private Command m_stow;
    private IntakeRollerCollectCommand m_collect;
    private Command m_feederAdvance;
    private Command m_cargoAdjust;
    private Command m_shooterReverse;

    public IntakeDeployCommand() {
        m_cameraOn = new CameraLightCommand();
        m_deploy = new IntakeArmDeployCommand();
        m_stow = new IntakeArmStowCommand();
        m_collect = new IntakeRollerCollectCommand();
        m_feederAdvance = new SequentialCommandGroup(
            new FeederAdvanceCommand(),
            new FeederExtraAdvanceCommand()
        );
        m_cargoAdjust = new CargoAdjustCommand();
        m_shooterReverse = new ShooterReverseCommand();
    }

    @Override
    public void initialize() {
        m_cameraOn.schedule();
        m_deploy.schedule();
        m_collect.schedule();
        m_shooterReverse.schedule();
    }

    @Override
    public void execute() {
        if (SensorSubsystem.getInstance().isCargoInIntake()) {
            if (SensorSubsystem.getInstance().isCargoInFeeder()) {
                // We have two cargo now.
                m_cargoAdjust.schedule();
                cancel();
            } else {
                m_feederAdvance.schedule();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_collect.cancel();
        m_shooterReverse.cancel();
        m_cameraOn.cancel();
        m_stow.schedule();
    }
}
