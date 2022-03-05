package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.team2357.frc2022.Constants;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DeployIntakeCommandGroup extends SequentialCommandGroup {

    private IntakeSubsystem m_intakeSub;

    public DeployIntakeCommandGroup(IntakeSubsystem intakeSub) {
        m_intakeSub = intakeSub;
        addCommands(new IntakeSetPivotCommand(m_intakeSub, Value.kForward),
                new WaitCommand(Constants.INTAKE.PIVOT_WAIT_SECONDS),
                new IntakeRollerCommand(m_intakeSub, Constants.INTAKE.FORWARD_SPEED));
        addRequirements(m_intakeSub);
    }

    @Override
    public void end(boolean interrupted) {
        new DeactivateIntakeCommandGroup(m_intakeSub).schedule();
    }

    @Override
    public boolean isFinished() {
        return m_intakeSub.isCargoInIntake() || super.isFinished();
    }
}
