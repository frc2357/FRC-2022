package com.team2357.frc2022.commands;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class IntakeSequenceCommandGroup extends SequentialCommandGroup{
private IntakeSubsystem m_intakeSub;

public IntakeSequenceCommandGroup(IntakeSubsystem intakeSub){
    m_intakeSub = intakeSub; 
    addRequirements(m_intakeSub);

if (m_intakeSub.getPivot() == Value.kReverse){
    addCommands(new IntakeSetPivotCommand(m_intakeSub, Value.kForward), new WaitCommand(0), new IntakeRollerCommand(m_intakeSub, Constants.INTAKE.FORWARD_SPEED));
    }

else {
    addCommands(new IntakeRollerStop(m_intakeSub,0), new WaitCommand(Constants.INTAKE.ROLLER_STOP_SECONDS), new IntakeSetPivotCommand(m_intakeSub, Value.kReverse));
    }
}
//overide
public boolean isFinished() {
    return false;
    }
}
