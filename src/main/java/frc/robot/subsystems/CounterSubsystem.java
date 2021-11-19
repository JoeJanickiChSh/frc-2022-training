// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class CounterSubsystem extends SmartSubsystemBase {

  private int count;
  private String name;

  public CounterSubsystem(String name) {
    this.name = name;
  }

  public CommandBase print() {
    return instant("Print", () -> {
      System.out.println(count);
    });
  }

  public CommandBase increment() {
    return instant("Increment", () -> {
      count++;
    });
  }

  public CommandBase incrementWhileRunning() {
    return running("Increment While Running", () -> {
      count++;
    }); // Is finished = false
  }

  public CommandBase decrement() {
    return instant("Decrement", () -> {
      count--;
    });
  }

  public CommandBase check(final int amount) {
    return new WaitUntilCommand(() -> count >= amount);
  }

  public CommandBase decrementWhileRunning() {
    return running("Decrement While Running", () -> {
      count--;
    });
  }

  @Override
  public void reset() {
    count = 0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Use this for any background processing
    SmartDashboard.putNumber(this.name + ": Count", count);
    SmartDashboard.putBoolean(this.name + ": Is Even ", (count & 1) == 0);
  }

  @Override
  public void safeState() {

  }
}
