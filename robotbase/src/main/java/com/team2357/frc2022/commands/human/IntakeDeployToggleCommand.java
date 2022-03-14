package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
import com.team2357.frc2022.commands.intake.IntakeStowCommand;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Moves the intake by calling setPivot on the {@link IntakeSubsystem}.
 * 
 * @category Intake
 */
public class IntakeDeployToggleCommand extends CommandLoggerBase {
    private IntakeArmSubsystem m_intakeArmSub;
    private IntakeRollerSubsystem m_intakeRollerSub;
    private SensorSubsystem m_sensorSub;

    /**
     * @param intakeSubsystem The {@link IntakeSubsystem}.
     */
    public IntakeDeployToggleCommand(IntakeArmSubsystem intakeArmSub, IntakeRollerSubsystem intakeRollerSub, SensorSubsystem sensorSub) {
        m_intakeArmSub = intakeArmSub;
        m_intakeRollerSub = intakeRollerSub;
        m_sensorSub = sensorSub;
        addRequirements(m_intakeArmSub);
        addRequirements(m_intakeRollerSub);
    }

    @Override
    public void initialize() {
        if (m_intakeArmSub.isStowed() || m_intakeArmSub.isStowing()) {
            (new IntakeDeployCommand(m_intakeArmSub, m_intakeRollerSub, m_sensorSub, 1)).schedule();
        } else {
            (new IntakeStowCommand(m_intakeArmSub, m_intakeRollerSub)).schedule();
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
