package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.climb.ClimberClimbToReachableCommandGroup;
import com.team2357.frc2022.commands.climb.ClimberClimbToRungCommandGroup;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimbProgressionCommand extends CommandLoggerBase {
    private SequentialCommandGroup climbCommands[] = {
            new ClimberClimbToReachableCommandGroup(),
            new ClimberClimbToRungCommandGroup(),
            new ClimberClimbToRungCommandGroup()
    };

    private int commandIndex;
    private long holdTimeMs;

    public ClimbProgressionCommand() {
        commandIndex = -1;
    }

    @Override
    public void initialize() {
        if (commandIndex > -1 && commandIndex < climbCommands.length) {
            climbCommands[commandIndex++].schedule();
        }

        if (commandIndex == -1) {
            holdTimeMs = System.currentTimeMillis() + 2000;
        }

        System.out.println("On command index: " + commandIndex);
    }

    @Override
    public void execute() {
        if (commandIndex == -1) {
            if (holdTimeMs < System.currentTimeMillis()) {
                climbCommands[++commandIndex].schedule();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return (commandIndex > -1);
    }
}
