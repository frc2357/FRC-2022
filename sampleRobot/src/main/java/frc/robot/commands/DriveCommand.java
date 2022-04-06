package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase{
    private XboxController m_controller;

    public DriveCommand(XboxController controller) {
        m_controller = controller;
        addRequirements(DriveSubsystem.getInstance());
    }

    @Override
    public void execute() {
        DriveSubsystem.getInstance().setDrive(m_controller.getLeftY(), m_controller.getRightX());
    }
}
