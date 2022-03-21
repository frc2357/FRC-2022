package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
import com.team2357.frc2022.commands.intake.IntakeStowCommand;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Moves the intake by calling setPivot on the {@link IntakeSubsystem}.
 * 
 * @category Intake
 */
public class IntakeDeployToggleCommand extends CommandLoggerBase {

    /**
     * @param intakeSubsystem The {@link IntakeSubsystem}.
     */
    public IntakeDeployToggleCommand() {
        }

    @Override
    public void initialize() {
        IntakeArmSubsystem intakeArm = IntakeArmSubsystem.getInstance();

        if (intakeArm.isStowed() || intakeArm.isStowing()) {
            new IntakeDeployCommand(1).schedule();
        } else {
            new IntakeStowCommand().schedule();
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
