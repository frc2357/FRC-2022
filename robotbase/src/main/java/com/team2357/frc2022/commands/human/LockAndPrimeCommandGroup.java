package com.team2357.frc2022.commands.human;

import java.util.concurrent.locks.Lock;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class LockAndPrimeCommandGroup extends ParallelCommandGroup {
    public LockAndPrimeCommandGroup() {
        addCommands(new TargetLockCommand());
        addCommands(new FireVisionCommand());
    }
    
}
