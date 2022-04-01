package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.commands.human.panic.ClimberWinchResetCommand;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberPullToRungCommandGroup extends SequentialCommandGroup {
    public ClimberPullToRungCommandGroup() {
        
        addCommands(new ParallelCommandGroup(new ClimberGoToRotationsCommand(Constants.CLIMBER.PULL_ONTO_RUNG_ROTATIONS),
                                             new ClimberSetLatchCommand(true)));
                                             
        addCommands(new ClimberSetLatchCommand(false));

        addCommands(new ClimberWinchResetCommand());

        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        super.initialize();
        ClimberSubsystem.getInstance().switchToLoadedClimberPID();
        System.out.println("Pull to rung");

    }
}
