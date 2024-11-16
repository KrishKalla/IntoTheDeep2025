package org.firstinspires.ftc.teamcode.subsystems;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.constants;

public class manipulator{
     private CRServo leftRotor = null;
     private CRServo rightRotor = null;

     private String state;

     public manipulator(){
          state = "PRESET";
     }

     public void init(HardwareMap map){
          state = "INIT";
          leftRotor = map.get(CRServo.class, "leftrotor");
          rightRotor = map.get(CRServo.class, "rightrotor");

          reset();
          state = "SET";
     }

     public void activate(constants.ROTOR_STATE state){
          switch (state){
               case OFF:
                    leftRotor.setPower(0);
                    rightRotor.setPower(0);
                    this.state = "OFF";
                    break;
               case ON:
                    leftRotor.setDirection(DcMotorEx.Direction.FORWARD);
                    rightRotor.setDirection(DcMotorEx.Direction.REVERSE);
                    leftRotor.setPower(1);
                    rightRotor.setPower(1);
                    this.state = "ON";
                    break;
               case REVERSE:
                    leftRotor.setDirection(DcMotorEx.Direction.REVERSE);
                    rightRotor.setDirection(DcMotorEx.Direction.FORWARD);
                    leftRotor.setPower(1);
                    rightRotor.setPower(1);
                    this.state = "REVERSE";
                    break;
               case RESET:
                    this.state = "MANUAL RESET IN PROGRESS...";
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
          state = "RESET";
     }

     @NonNull
     @Override
     public String toString(){
          return state;
     }
}