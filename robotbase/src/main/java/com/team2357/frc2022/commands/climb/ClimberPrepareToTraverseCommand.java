package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ClimberPrepareToTraverseCommand extends SequentialCommandGroup{
    public ClimberPrepareToTraverseCommand() {
        addCommands(new ClimberExtendToRungCommandGroup());
        addCommands(new ClimberSetUprightCommand(true));

        addCommands(new WaitCommand(Constants.CLIMBER.CLIMBER_TIME_TO_UPRIGHT_SECONDS));

        addCommands(new ClimberSettleToRungCommand());

        addRequirements(ClimberSubsystem.getInstance());
    }   
}