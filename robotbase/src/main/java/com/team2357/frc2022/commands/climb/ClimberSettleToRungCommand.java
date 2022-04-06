package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ClimberSettleToRungCommand extends SequentialCommandGroup {
    public ClimberSettleToRungCommand() {
        addCommands(new ClimberSetUprightCommand(false));
        addCommands(new ClimberGoToRotationsCommand(Constants.CLIMBER.SETTLE_TO_RUNG_ROTATIONS));

        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        super.initialize();
        ClimberSubsystem.getInstance().switchToUnloadedClimberPID();
    }
}
