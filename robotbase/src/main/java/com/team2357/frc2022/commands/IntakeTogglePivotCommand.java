package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

//TODO: Make Noah write this one
/**
 * Moves the intake by calling setPivot on the {@link IntakeSubsystem}.
 * 
 * @category Intake
 */
public class IntakeTogglePivotCommand extends CommandLoggerBase {
    private IntakeSubsystem m_intakeSub;

    /**
     * @param intakeSubsystem The {@link IntakeSubsystem}.
     */
    public IntakeTogglePivotCommand(IntakeSubsystem intakeSub) {
        m_intakeSub = intakeSub;
        addRequirements(m_intakeSub);
    }

    @Override
    public void initialize() {
        m_intakeSub.setPivot(m_intakeSub.getPivot() == Value.kReverse ?Value.kForward: Value.kReverse);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}