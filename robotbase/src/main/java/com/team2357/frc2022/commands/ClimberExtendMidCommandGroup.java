package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberExtendMidCommandGroup extends SequentialCommandGroup {
    ClimberSubsystem m_climbSub;

    ClimberExtendMidCommandGroup(ClimberSubsystem climbSub) {
        m_climbSub = climbSub;

        addCommands(new ClimberSetPivotCommand(m_climbSub, DoubleSolenoid.Value.kForward));
        addCommands(new WaitCommand(Constants.CLIMBER.PIVOT_UPRIGHT_SECONDS));
        addCommands(new ClimberExtendCommand(m_climbSub, Constants.CLIMBER.CLIMB_EXTEND_SPEED,
                Constants.CLIMBER.MID_RUNG_EXTENSION_METERS));

        addRequirements(m_climbSub);
    }

    @Override
    public void end(boolean interrupted) {
        m_climbSub.handleState(ClimberSubsystem.State.EXTEND_MID_RUNG);
    }
}
