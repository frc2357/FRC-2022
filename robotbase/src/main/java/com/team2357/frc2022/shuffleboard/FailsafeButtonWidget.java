package com.team2357.frc2022.shuffleboard;

// TODO: ShuffleBoard Implement
/**
 * This class should have a option to toggle failsafe mode.
 * Should add a widget to a given shuffleboard tab that:
 * Creates a widget on given tab that has label "FAILSAFE" and is a toggleButton
 * Creates a toggleTrigger based on the created widget
 * The trigger should set the subsystems to failsafe mode when it is active, and
 * inactive when the trigger is inactive
 * 
 * Further information
 * 
 * The method setClosedLoopEnabled is on all our subsystems
 * If it is set to true, the subsystem will not be in closed loop mode
 * If it is set to false, the subsystem will be in open loop mode
 * 
 * If in failsafe mode, all subsystems should be in open loop mode
 * 
 * Closed loop vs. open loop
 * Closed loop: Susbystem is relying on sensors to track position and function
 * Open loop: Subsystem is only taking input from human controllers, and does
 * not have any positional data.
 * 
 * You should use the FailsafeCommand to set subsystems to their respective
 * closed loop state
 * 
 */
public class FailsafeButtonWidget {

}
