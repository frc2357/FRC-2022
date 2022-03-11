package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.KickerSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootAgainstHubCommand extends CommandLoggerBase{
    private KickerSubsystem m_kickerSub;
    private FeederSubsystem m_feederSub;
    private ShooterSubsystem m_shooterSub;

    public ShootAgainstHubCommand(KickerSubsystem kickerSub, FeederSubsystem feederSub, ShooterSubsystem shooterSub){
        m_kickerSub = kickerSub;
        m_feederSub = feederSub;
        m_shooterSub = shooterSub;
        addRequirements(m_kickerSub, m_feederSub, m_shooterSub);
    }

    @Override
    public void initialize(){
        super.initialize();
        m_shooterSub.setRPMTop(Constants.SHOOTER.SHOOT_AGAINST_HUB.SHOOTER_SPEED_TOP);
        m_shooterSub.setRPMBottom(Constants.SHOOTER.SHOOT_AGAINST_HUB.SHOOTER_SPEED_BOTTOM);
        new WaitCommand(Constants.SHOOTER.SHOOT_AGAINST_HUB.WAIT_AFTER_SHOOTER);
        m_kickerSub.runKickerMotor(Constants.SHOOTER.SHOOT_AGAINST_HUB.KICKER_SPEED);
        m_feederSub.runFeedermotor(Constants.SHOOTER.SHOOT_AGAINST_HUB.FEEDER_SPEED);
        
    }

    @Override
    public void end(boolean interrupted){
        super.end(interrupted);
        m_kickerSub.runKickerMotor(0.0);
        m_feederSub.runFeedermotor(0.0);
        m_shooterSub.setRPMTop(0.0);
        m_shooterSub.setRPMBottom(0.0);
    }
}
