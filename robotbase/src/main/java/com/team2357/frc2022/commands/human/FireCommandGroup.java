package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.intake.IntakeToFeederCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

public class FireCommandGroup extends ParallelRaceGroup {

    public FireCommandGroup() {
        addRequirements(ShooterSubsystem.getInstance(), IntakeRollerSubsystem.getInstance(), FeederSubsystem.getInstance());

        addCommands(new FireCommand());
        //addCommands(new FeederToShooterCommand());
        addCommands(new IntakeToFeederCommand());
    }
}
