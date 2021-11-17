// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
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

    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    final private SendableChooser<Command> autoChooser = new SendableChooser<>();

    private final ButtonXboxController pilotController = new ButtonXboxController(0);

    public static class CountCommand extends CommandBase {
        int counter;

        @Override
        public void initialize() {
            counter = 0;
        }

        @Override
        public void execute() {
            counter++;
        }

        @Override
        public boolean isFinished() {
            return counter >= 50 * 2;
        }

        @Override
        public void end(boolean interrupted) {
            System.out.println("Done. " + ((interrupted) ? ("Was Interuppted") : ("")));
        }
    }

    /** Set up the button bindings. */
    @Override
    public void configureButtonBindings() {
        pilotController.getButton(Button.kX).whileHeld(startEnd("Billy", () -> {
            System.out.println("Hello, Billy");
            pilotController.setRumble(RumbleType.kLeftRumble, 1.);
        }, () -> {
            System.out.println("Goodbye, Billy");
            pilotController.setRumble(RumbleType.kLeftRumble, 0.);
        }));
        pilotController.getButton(Button.kY).whenPressed(cmd("Built").onInitialize(() -> {
            System.out.println("Hello, Billy2");
            pilotController.setRumble(RumbleType.kLeftRumble, 1.);
        }).onEnd(interrupted -> {
            System.out.println("Goodbye, Billy2");
            pilotController.setRumble(RumbleType.kLeftRumble, 0.);
        }).finishedWhen(() -> false));

        pilotController.getButton(Button.kB).whenPressed(
                sequence("B_Sequence", new PrintCommand("Starting"), new CountCommand(), new PrintCommand("Ending")));

        // pilotController.getButton(Button.kA).whenPressed(new InstantCommand(() -> {
        // System.out.println(2 + 2);
        // }));
        // CountCommand counter = new CountCommand();
        // pilotController.getButton(Button.kB).whenPressed(counter);
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
