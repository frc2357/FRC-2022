package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import com.team2357.frc2022.Constants;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

<<<<<<< HEAD:robotbase/src/main/java/com/team2357/frc2022/commands/IntakeDeactivateCommandGroup.java
public class IntakeDeactivateCommandGroup extends SequentialCommandGroup {
    private IntakeSubsystem m_intakeSub;

    public IntakeDeactivateCommandGroup(IntakeSubsystem intakeSub) {
        System.out.println("Deactivating Intake");
        m_intakeSub = intakeSub;
=======
/**
 * @category Intake
 */
public class IntakeStowCommandGroup extends SequentialCommandGroup {
    private IntakeSubsystem m_intakeSub;

    public IntakeStowCommandGroup(IntakeSubsystem intakeSub) {
>>>>>>> d8d4a5f890fa2c47c5aa9a16611af003d7f3f739:robotbase/src/main/java/com/team2357/frc2022/commands/IntakeStowCommandGroup.java
        addCommands(new IntakeRollerStop(m_intakeSub), new WaitCommand(Constants.INTAKE.ROLLER_STOP_SECONDS),
                new IntakeSetPivotCommand(m_intakeSub, Value.kReverse));
        addRequirements(m_intakeSub);
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
