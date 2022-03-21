package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.commands.feeder.FeederPackCommand;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Deploys the intake arm.
 * 
 * @category Intake
 */
public class IntakeDeployCommand extends CommandLoggerBase {
    public IntakeDeployCommand() {
        addRequirements(IntakeArmSubsystem.getInstance());
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override 
    public void initialize() {
        IntakeArmSubsystem intakeArm = IntakeArmSubsystem.getInstance();
        IntakeRollerSubsystem intakeRoller = IntakeRollerSubsystem.getInstance();

        intakeArm.deploy();
        intakeRoller.collect();
    }

    @Override
    public boolean isFinished() {
        SensorSubsystem sensors = SensorSubsystem.getInstance();
        return sensors.isCargoInFeeder() && sensors.isCargoInIndex();
    } 

    @Override
    public void end(boolean interrupted) {
        if (isFinished()) {
            new FeederPackCommand().schedule();
        }

        IntakeArmSubsystem.getInstance().stow();
        IntakeRollerSubsystem.getInstance().stop();
    }
}