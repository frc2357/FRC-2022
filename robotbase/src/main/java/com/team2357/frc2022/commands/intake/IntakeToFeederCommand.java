package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeToFeederCommand extends CommandLoggerBase {
    private boolean m_hasCargoInIntake;

    public IntakeToFeederCommand() {
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        m_hasCargoInIntake = (SensorSubsystem.getInstance().getCurrentCargoCount() >= 2);
    }

    @Override
    public void execute() {
        if (m_hasCargoInIntake && ShooterSubsystem.getInstance().atTargetSpeed() && !SensorSubsystem.getInstance().isCargoInFeeder()) {
            //IntakeRollerSubsystem.getInstance().setIntakeRollerSpeed(Constants.INTAKE.INTAKE_TO_FEEDER);
        } else {
            IntakeRollerSubsystem.getInstance().stop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRollerSubsystem.getInstance().stop();
    }

    @Override
    public boolean isFinished() {
        return SensorSubsystem.getInstance().getCurrentCargoCount() <= 0;
    }
}
