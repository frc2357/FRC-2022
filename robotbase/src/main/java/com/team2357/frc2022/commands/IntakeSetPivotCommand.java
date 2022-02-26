package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Moves the intake by calling setPivot on the {@link IntakeSubsystem}.
 * 
 * @category Intake
 */
public class IntakeSetPivotCommand extends CommandLoggerBase {
    private IntakeSubsystem m_intakeSub;
    private Value m_postion;

    /**
     * @param intakeSubsystem The {@link IntakeSubsystem}.
     */
    public IntakeSetPivotCommand(IntakeSubsystem intakeSub, Value postion) {
        m_intakeSub = intakeSub;
        m_postion = postion;
        addRequirements(m_intakeSub);
    }

    @Override
    public void initialize() {
        m_intakeSub.setPivot(m_postion);
    }

    @Override
    public void end(boolean interrupted) {
        m_intakeSub.setPivot(Value.kOff);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}