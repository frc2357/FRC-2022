package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.team2357.frc2022.Constants;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * @category Intake
 */
public class IntakeDeployCommandGroup extends SequentialCommandGroup {

    private IntakeSubsystem m_intakeSub;

    public IntakeDeployCommandGroup(IntakeSubsystem intakeSub) {
        m_intakeSub = intakeSub;
        addCommands(new IntakeSetPivotCommand(m_intakeSub, Value.kForward),
                new IntakeRollerCommand(m_intakeSub, Constants.INTAKE.FORWARD_SPEED));
        addRequirements(m_intakeSub);
        System.out.println("Constructing intake deploy");
    }

    @Override
    public void execute() {
        super.execute();
        System.out.println("Executing intake deploy");
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Ending intake deploy group");
        super.end(interrupted);
        new IntakeStowCommandGroup(m_intakeSub).schedule();
    }

    @Override
    public boolean isFinished() {
        return m_intakeSub.isCargoInIntake() || super.isFinished();
    }
}
