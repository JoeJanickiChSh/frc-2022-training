// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;
import com.chopshop166.chopshoplib.controls.ButtonXboxController.Direction;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.subsystems.CounterSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import io.github.oblarg.oblog.Logger;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends CommandRobot {

    private Command autonomousCommand;

    private final CounterSubsystem counterA = new CounterSubsystem("Counter A");
    private final CounterSubsystem counterB = new CounterSubsystem("Counter B");

    final private SendableChooser<Command> autoChooser = new SendableChooser<>();

    private final ButtonXboxController pilotController0 = new ButtonXboxController(0);
    private final ButtonXboxController pilotController1 = new ButtonXboxController(1);

    /** Set up the button bindings. */
    @Override
    public void configureButtonBindings() {
        pilotController0.getButton(Button.kA).whileHeld(parallel("a++,b++", counterA.incrementWhileRunning(),
                sequence("a>50?b++", counterA.check(50), counterB.incrementWhileRunning())));

    }

    /** Send commands and data to Shuffleboard. */
    @Override
    public void populateDashboard() {
    }

    /** Set the default commands for each subsystem. */
    @Override
    public void setDefaultCommands() {
    }

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        super.robotInit();
        Logger.configureLoggingAndConfig(this, false);
    }

    @Override
    public void robotPeriodic() {
        super.robotPeriodic();
        Logger.updateEntries();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
        super.disabledInit();
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = autoChooser.getSelected();

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }
}
