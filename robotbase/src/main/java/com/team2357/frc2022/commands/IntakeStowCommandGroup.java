package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.team2357.frc2022.Constants;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * @category Intake
 */
public class IntakeStowCommandGroup extends SequentialCommandGroup {
    private IntakeSubsystem m_intakeSub;

    public IntakeStowCommandGroup(IntakeSubsystem intakeSub) {
        m_intakeSub = intakeSub;
        addCommands(new IntakeRollerStop(m_intakeSub), new WaitCommand(Constants.INTAKE.ROLLER_STOP_SECONDS),
                new IntakeSetPivotCommand(m_intakeSub, Value.kReverse));
        addRequirements(m_intakeSub);
        System.out.println("Running stow constructor");
    }

    @Override
    public void initialize(){
        super.initialize();
        System.out.println("Running deactivate intake");
    }

    @Override
    public void execute(){
        super.execute();
        System.out.println("Running deactivate intake");
    }
}
