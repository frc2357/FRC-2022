package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.KickerSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

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
        m_kickerSub.runKickerMotor(Constants.SHOOT_HUB_SPEEDS.KICKER_SPEED);
        m_feederSub.runFeedermotor(Constants.SHOOT_HUB_SPEEDS.FEEDER_SPEED);
        m_shooterSub.setRPMTop(Constants.SHOOT_HUB_SPEEDS.SHOOTER_SPEED_TOP);
        m_shooterSub.setRPMBottom(Constants.SHOOT_HUB_SPEEDS.SHOOTER_SPEED_BOTTOM);
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
