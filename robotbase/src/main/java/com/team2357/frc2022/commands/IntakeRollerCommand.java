package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj.PowerDistribution;

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
    private PowerDistribution m_pdh;

    private double m_currentLimit;
    private double m_speed;

    private boolean m_isFinished;

    private double m_timer;

    /**
     * @param intakeSub The {@link IntakeSubsystem}.
     */
    public IntakeRollerCommand(IntakeSubsystem intakeSub, double speed) {
        m_intakeSub = intakeSub;
        m_speed = speed;
        m_pdh = null;
        m_currentLimit = 0;
        addRequirements(m_intakeSub);
    }

    /**
     * @param intakeSub The {@link IntakeSubsystem}.
     */
    public IntakeRollerCommand(IntakeSubsystem intakeSub, double speed, PowerDistribution pdh,
            double currentLimit) {
        m_intakeSub = intakeSub;
        m_speed = speed;
        m_pdh = pdh;
        m_currentLimit = currentLimit;
        addRequirements(m_intakeSub);
    }

    @Override
    public void initialize() {
        super.initialize();
        m_isFinished = false;
        m_timer = System.currentTimeMillis();
        m_intakeSub.triggerIntakeRoller(m_speed);
    }

    @Override
    public void execute() {
        System.out.println(m_pdh.getCurrent(
            Constants.POWER_DISTRIBUTION_BOARD_ID.INTAKE_CHANNEL));
        if (m_pdh != null) {
            if ((m_pdh.getCurrent(
                    Constants.POWER_DISTRIBUTION_BOARD_ID.INTAKE_CHANNEL) > m_currentLimit) && (m_timer+500 < System.currentTimeMillis()) ) {
                m_isFinished = true;
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        m_intakeSub.triggerIntakeRoller(0.0);
    }

    @Override
    public boolean isFinished() {
        return m_isFinished;
    }
}