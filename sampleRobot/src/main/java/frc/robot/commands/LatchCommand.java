package frc.robot.commands;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class LatchCommand extends CommandBase {
    private boolean m_openHook = false;
    private Solenoid m_latchSolenoid;

    public LatchCommand() {
        m_latchSolenoid = new Solenoid(Constants.CAN_ID.PNEUMATICS_HUB_ID, PneumaticsModuleType.REVPH, Constants.LATCH_ID);
    }

    @Override
    public void initialize() {
        m_openHook = !m_openHook;
        m_latchSolenoid.set(m_openHook);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
