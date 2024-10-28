package org.firstinspires.ftc.teamcode.subsystems;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.constants;

public class manipulator{
     private CRServo leftRotor = null;
     private CRServo rightRotor = null;

     private String rotor;

     public manipulator(){
          rotor = "PRESET";
     }

     public void init(HardwareMap map){
          rotor = "INIT";
          leftRotor = map.get(CRServo.class, "leftrotor");
          rightRotor = map.get(CRServo.class, "rightrotor");

          reset();
          rotor = "SET";
     }

     public void activate(constants.ROTOR_STATE state){
          switch (state){
               case OFF:
                    leftRotor.setPower(0);
                    rightRotor.setPower(0);
                    rotor = "OFF";
                    break;
               case ON:
                    leftRotor.setDirection(DcMotorEx.Direction.FORWARD);
                    rightRotor.setDirection(DcMotorEx.Direction.REVERSE);
                    leftRotor.setPower(1);
                    rightRotor.setPower(1);
                    rotor = "ON";
                    break;
               case REVERSE:
                    leftRotor.setDirection(DcMotorEx.Direction.REVERSE);
                    rightRotor.setDirection(DcMotorEx.Direction.FORWARD);
                    leftRotor.setPower(1);
                    rightRotor.setPower(1);
                    rotor = "REVERSE";
                    break;
               case RESET:
                    rotor = "MANUAL RESET IN PROGRESS...";
                    reset();
                    break;
               default:
                    reset();
                    break;
          }
     }


     public void reset(){
          leftRotor.setDirection(DcMotorEx.Direction.FORWARD);
          rightRotor.setDirection(DcMotorEx.Direction.REVERSE);
          leftRotor.setPower(0);
          rightRotor.setPower(0);
          rotor = "RESET";
     }

     @NonNull
     @Override
     public String toString(){
          return rotor;
     }
}