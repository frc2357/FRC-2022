package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * @ category Intake
 */
/**
 * This command runs the intake roller from {@link IntakeSubsystem}.
 * 
 * @category Intake
 */
public class IntakeRollerCommand extends CommandLoggerBase {
    private IntakeSubsystem m_intakeSub;
    private double m_speed;

    /**
     * @param intakeSub The {@link IntakeSubsystem}.
     */
    public IntakeRollerCommand(IntakeSubsystem intakeSub, double speed) {
        m_intakeSub = intakeSub;
        m_speed = speed;
        addRequirements(m_intakeSub);
    }

    @Override
    public void initialize() {
        super.initialize();
        m_intakeSub.triggerIntakeRoller(m_speed);
    }

    @Override
    public void execute(){
        System.out.println("Running intake");
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        m_intakeSub.triggerIntakeRoller(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}