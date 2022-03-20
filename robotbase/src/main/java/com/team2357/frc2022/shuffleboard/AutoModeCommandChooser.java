package com.team2357.frc2022.shuffleboard;


import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoModeCommandChooser {
    public enum AutomodeActions {
        NONE,
    }

    private class AutoActionChooser {
        protected SendableChooser<AutomodeActions> m_chooser;
        protected String m_waitCommandKey;

        protected AutoActionChooser(int index) {
            m_waitCommandKey = "wait " + index;
            m_chooser = new SendableChooser<>();

            m_chooser.setDefaultOption("None", AutomodeActions.NONE);

            SmartDashboard.putNumber((m_waitCommandKey),0.0);
            SmartDashboard.putData("Auto chooser "+index,m_chooser);

        }

        public Command getWaitCommand() {
            double waitTime = SmartDashboard.getNumber(m_waitCommandKey, 0.0);
            return new WaitCommand(waitTime);
        }
        
        public Command getActionCommand() {
            switch(m_chooser.getSelected()) {
                case NONE:
                default:
                    System.out.println("ACTION: NONE");
                    return new WaitCommand(0);
            }
        }
    }

    private AutoActionChooser[] choosers;



    public AutoModeCommandChooser() {

        choosers = new AutoActionChooser[3];
        choosers[0] = new AutoActionChooser(0);
        choosers[1] = new AutoActionChooser(1);
        choosers[2] = new AutoActionChooser(2);
    }

    public Command generateCommand() {
        return new SequentialCommandGroup(
        choosers[0].getWaitCommand(),
        choosers[0].getActionCommand(),

        choosers[1].getWaitCommand(),
        choosers[1].getActionCommand(),

        choosers[2].getWaitCommand(),
        choosers[2].getActionCommand()

        );

    }
}