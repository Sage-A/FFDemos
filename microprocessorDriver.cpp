#include "mbed.h"
//triggers: CDS Cell on or off
//audio enabled 
// timer up
//timer down
//enabled or disabled 1-4
// on or off led 1-4

// Attach pins to variables
DigitalIn CDS(p5);
DigitalIn audioEn(p6);
InterruptIn unitSwap(p17);
InterruptIn timeUp(p7);
InterruptIn timeDown(p8);
DigitalIn ledEn1(p9);
DigitalIn ledEn2(p10);
DigitalIn ledEn3(p11);
DigitalIn ledEn4(p12);
DigitalIn ledStatus1(p13);
DigitalIn ledStatus2(p14);
DigitalIn ledStatus3(p15);
DigitalIn ledStatus4(p16);

Timer timer;

PwmOut sound(p21);
BusOut display(p28,p27,p19,p18,p20,p29,p30);

//Create constants for display
int one = 0b0000110;
int two = 0b1011011;
int three = 0b1001111;
int four = 0b1100110;
int five = 0b1101101;
int six = 0b1111101;
int seven = 0b0000111;
int eight = 0b1111111;
int nine = 0b1100111;
int zero = 0b0111111;
int off = 0b0000000;
int m = 0b0101011;
int s = 0b1101101;

volatile int seconds = 30;
volatile int minutes = 0;
volatile int unit = 0; // 0 is sec, 1 is minute

//Show given time on 7-seg display (no units)
void parseTime(int time){
    //Can display values between 0 - 99
    switch(time / 10){ //display 10s place value
            case 0:
                display = off;
                break;
            case 1:
                display = one;
                break;
            case 2:
                display = two;
                break;
            case 3:
                display = three;
                break;
            case 4:
                display = four;
                break;
            case 5:
                display = five;
                break;
            case 6:
                display = six;
                break;
            case 7:
                display = seven;
                break;
            case 8:
                display = eight;
                break;
            case 9:
                display = nine;
                break;
            default:
                display = off;
        }
        wait(0.4);
        display = off;
        wait(0.1);
        switch(time % 10){ //Display 1s digit of value
            case 0:
                display = zero;
                break;
            case 1:
                display = one;
                break;
            case 2:
                display = two;
                break;
            case 3:
                display = three;
                break;
            case 4:
                display = four;
                break;
            case 5:
                display = five;
                break;
            case 6:
                display = six;
                break;
            case 7:
                display = seven;
                break;
            case 8:
                display = eight;
                break;
            case 9:
                display = nine;
                break;
            default:
                display = off;
        }
         wait(0.4);
        display = off;
}

//Show unit on 7-seg display (1 = minute, else seconds)
void parseUnit(int value){
    if(value == 1){
        display = m;
    }
    else{
        display = s;
    }
    wait(0.4);
    display = off;
}

//Increase timer limit by 1 (current unit) and display results
void incTime(){
    if(unit == 0){
        //sec
        if(seconds <= 60){
            seconds++;
        }
        else{
            seconds = 0;
        }
        parseTime(seconds);
    }
    else{
        //min
        if(minutes <= 60){
            minutes++;
        }
        else{
            minutes = 0;
        }
        parseTime(minutes);
    }
    
}

//Decrease timer limit by 1 (current unit) and display new value
void decTime(){
    if(unit == 0){
        //sec
        if(seconds >= 0){
            seconds--;
        }
        else{
            seconds = 60;
        }
        parseTime(seconds);
    }
    else{
        //min
        if(minutes >= 0){
            minutes--;
        }
        else{
            minutes = 60;
        }
        parseTime(minutes);
    }
}

//Change current unit from sec<->min and display new unit choice
void changeUnit(){
    if(unit == 1){
        unit = 0;
    }
    else {
        unit = 1;
    }
    parseUnit(unit);
}

//Show current timer limits and units
void displayTimer(){
    parseTime(minutes);
    parseUnit(1);
    parseTime(seconds);
    parseUnit(0);
}


int main() {
    //Attach functions to buttons
    timeUp.rise(&incTime);
    timeDown.rise(&decTime);
    unitSwap.rise(&changeUnit);
    sound.period(1.0/1000);

    while(1){
        while(CDS == 0){
            //do nothing until cds goes on
        }
        timer.start(); // start timer
        while(timer.read() < (seconds + (minutes * 60))){
            //do nothing while waiting for time limit to elapse
            displayTimer();
            wait(3);
        }

    if(audioEn == 1){ //Check status of audio enabled switch
    //If any leds are enabled and on, turn on alarm
        if(ledEn1 == 1 & ledStatus1 == 1){
            sound = 0.5;
        }
        else if(ledEn2 == 1 & ledStatus2 == 1){
            sound = 0.5;
        }
        else if(ledEn3 == 1 & ledStatus3 == 1){
            sound = 0.5;
        }
        else if(ledEn4 == 1 & ledStatus4 == 1){
            sound = 0.5;
        }
    }

    while(ledStatus1 == 1 | ledStatus2 == 1 | ledStatus3 == 1 | ledStatus4 ==1){
        //do nothing until all leds go off
    }

    //Sound off and reset timer
    sound = 0;
    timer.stop();
    timer.reset();
    while(CDS == 1){
        //wait for CDS to turn off
    }
    
    //wait for voltages to settle then restart
    wait(0.2);
    }
    
}
