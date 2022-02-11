package com.team2357.frc2022.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// TODO: ShuffleBoard Implement
/**
 * This class should control a chooser to select up to three actions with a wait
 * command between them
 * Do not worry about making the actual auto commands, just the chooser widget
 * that can select different
 * things is enough for now
 */
public class AutoModeCommandChooser {
    public enum AutomodeActions {
        NONE,
        MOVE_OFF_LINE,
        SHOOT,
    }

    private class AutoActionChooser {
        protected NetworkTableEntry m_waitWidget;
        protected SendableChooser<AutomodeActions> m_chooser;

        protected AutoActionChooser(int index) {
            ShuffleboardTab tab = Shuffleboard.getTab(m_tabTitle);
            m_chooser = new SendableChooser<>();

            m_chooser.setDefaultOption("None", AutomodeActions.NONE);
            m_chooser.addOption("Shoot", AutomodeActions.SHOOT);
            m_chooser.addOption("Move Off Line", AutomodeActions.MOVE_OFF_LINE);
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

        choosers[0].getWaitCommand(),
        choosers[0].getActionCommand(),

        choosers[0].getWaitCommand(),
        choosers[0].getActionCommand()

        );

    }
}