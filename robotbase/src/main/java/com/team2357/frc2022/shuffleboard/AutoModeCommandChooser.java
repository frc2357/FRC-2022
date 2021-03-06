package com.team2357.frc2022.shuffleboard;

import com.team2357.frc2022.commands.auto.AutoNoTrajectoryThreeBallCommand;
import com.team2357.frc2022.commands.auto.FiveBallAutoCommand;
import com.team2357.frc2022.commands.auto.OneBallAutoCommand;
import com.team2357.frc2022.commands.auto.TwoBallAutoCommand;
import com.team2357.frc2022.commands.auto.ThreeBallAutoCommand;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoModeCommandChooser {

    private AutoActionChooser[] choosers;

    public enum AutomodeActions {
        NONE,
        ONE_BALL,
        TWO_BALL,
        THREE_BALL,
        FIVE_BALL
    }

    private class AutoActionChooser {
        protected SendableChooser<AutomodeActions> m_chooser;
        protected String m_waitCommandKey;

        protected AutoActionChooser(int index) {
            m_waitCommandKey = "wait " + index;
            m_chooser = new SendableChooser<>();

            m_chooser.setDefaultOption("None", AutomodeActions.NONE);
            m_chooser.addOption("One Ball", AutomodeActions.ONE_BALL);
            m_chooser.addOption("Two Ball", AutomodeActions.TWO_BALL);
            m_chooser.addOption("Three Ball", AutomodeActions.THREE_BALL);
           // m_chooser.addOption("Five Ball", AutomodeActions.FIVE_BALL);

            SmartDashboard.putNumber((m_waitCommandKey), 0.0);
            SmartDashboard.putData("Auto chooser " + index, m_chooser);
        }

        public Command getWaitCommand() {
            double waitTime = SmartDashboard.getNumber(m_waitCommandKey, 0.0);
            return new WaitCommand(waitTime);
        }

        public Command getActionCommand() {
            switch (m_chooser.getSelected()) {
                case ONE_BALL:
                    System.out.println("ONE BALL");
                    return new OneBallAutoCommand();
                case TWO_BALL:
                    System.out.println("TWO BALL");
                    return new TwoBallAutoCommand();
                case THREE_BALL:
                    System.out.println("THREE BALL");
                    return new ThreeBallAutoCommand();
                case FIVE_BALL:
                    System.out.println("FIVE BALL");
                   // return new FiveBallAutoCommand();
                case NONE:
                default:
                    System.out.println("ACTION: NONE");
                    return new WaitCommand(0);
            }
        }
    }

    public AutoModeCommandChooser() {
        choosers = new AutoActionChooser[3];
        choosers[0] = new AutoActionChooser(0);
    }

    public Command generateCommand() {
        return new SequentialCommandGroup(
                choosers[0].getWaitCommand(),
                choosers[0].getActionCommand());
    }
}