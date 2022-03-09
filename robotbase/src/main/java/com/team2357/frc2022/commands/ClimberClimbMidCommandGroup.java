package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberClimbMidCommandGroup extends SequentialCommandGroup {
    ClimberSubsystem m_climbSub;

    ClimberClimbMidCommandGroup(ClimberSubsystem climbSub) {
        m_climbSub = climbSub;

        addCommands(new ClimberRetractCommand(m_climbSub, Constants.CLIMBER.CLIMB_RETURN_SPEED,
                Constants.CLIMBER.MID_RUNG_RETURN_ROTATIONS, Constants.CLIMBER.ON_BAR_AMPS));

        addRequirements(m_climbSub);
    }

    @Override
    public void end(boolean interrupted) {
        m_climbSub.handleState(ClimberSubsystem.State.ON_MID_RUNG);
    }
}
