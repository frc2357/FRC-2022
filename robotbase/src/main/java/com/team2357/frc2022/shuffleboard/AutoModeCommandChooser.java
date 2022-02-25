package com.team2357.frc2022.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoModeCommandChooser {
    public enum AutomodeActions {
        NONE,
    }

    private class AutoActionChooser {
        protected NetworkTableEntry m_waitWidget;
        protected SendableChooser<AutomodeActions> m_chooser;

        protected AutoActionChooser(int index) {
            ShuffleboardTab tab = Shuffleboard.getTab(m_tabTitle);
            m_chooser = new SendableChooser<>();

            m_chooser.setDefaultOption("None", AutomodeActions.NONE);
            tab.add("Auto Action " + index, m_chooser);

            NetworkTableEntry waitWidget = Shuffleboard.getTab(m_tabTitle)
                .add("Wait Time " + index, 0)
                .withWidget(BuiltInWidgets.kTextView).getEntry();

            m_waitWidget = waitWidget;
        }

        public Command getWaitCommand() {
            double waitTime = m_waitWidget.getDouble(0);
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

    private static String m_tabTitle;
    private AutoActionChooser[] choosers;


    public AutoModeCommandChooser(String tabTitle) {
        m_tabTitle = tabTitle;

        choosers = new AutoActionChooser[3];
        choosers[0] = new AutoActionChooser(0);
        choosers[1] = new AutoActionChooser(1);
        choosers[2] = new AutoActionChooser(2);
    }

    public static void show() {
        Shuffleboard.selectTab((m_tabTitle));
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