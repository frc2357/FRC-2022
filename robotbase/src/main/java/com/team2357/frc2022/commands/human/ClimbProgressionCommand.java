package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.climb.ClimberClimbToReachableCommandGroup;
import com.team2357.frc2022.commands.climb.ClimberExtendToReachableCommandGroup;
import com.team2357.frc2022.commands.climb.ClimberPrepareToTraverseCommand;
import com.team2357.frc2022.commands.climb.ClimberPullToRungCommandGroup;
import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimbProgressionCommand extends CommandLoggerBase {
    private SequentialCommandGroup climbCommands[] = {
            new ClimberExtendToReachableCommandGroup(),
            new ClimberClimbToReachableCommandGroup(),
            new ClimberPrepareToTraverseCommand(),
            new ClimberPullToRungCommandGroup(),
            new ClimberPrepareToTraverseCommand(),
            new ClimberPullToRungCommandGroup()
    };


    public ClimbProgressionCommand() {
        ClimberSubsystem.getInstance().setCommandIndex(0);
        ClimberSubsystem.getInstance().resetEncoders();
    }

    @Override
    public void initialize() {
        int commandIndex = ClimberSubsystem.getInstance().getCommandIndex();

        if (commandIndex < climbCommands.length) {
            climbCommands[commandIndex].schedule();
            ClimberSubsystem.getInstance().setCommandIndex(commandIndex++);
            System.out.println("Scheduling command");
        }

        System.out.println("On command index: " + commandIndex);
    }

    @Override
    public void end(boolean interrupted) {
        if(ClimberSubsystem.getInstance().getCommandIndex() == climbCommands.length + 1) {
            ClimberSubsystem.getInstance().setCommandIndex(0);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
