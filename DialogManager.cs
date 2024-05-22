using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.UI;
using System;

public class DialogManager : MonoBehaviour
{
    
    [SerializeField] GameObject dialogBox;
    [SerializeField] TextMeshProUGUI dialogText;
    int lettersPerSecond = 20;

    Dialog dialog; //global copy of ShowDialog parameter
    int currentLine = 0; //current line of dialog lines array
    bool isTyping = false; // if dialog is being currently typed
    bool isComplete = false; //if all dialog lines have run
    bool choiceMade = true; //if button choice selected
    bool dialogState = false;
    int buttonChoice = -1; //button selected, 0-5; -1 is unselected

    public GameObject playerAnswers;
    public Button ansA;
    public Button ansB;
    public Button ansC;
    public Button ansD;
    public Button ansE;
    public Button ansF;

    public GameObject batchAnswers;
    public TextMeshProUGUI ansTextA;
    public TextMeshProUGUI ansTextB;
    public TextMeshProUGUI ansTextC;
    public TextMeshProUGUI ansTextD;
    public TextMeshProUGUI ansTextE;
    public TextMeshProUGUI ansTextF;

    int numChoices = 0;
    


//coroutine
    public IEnumerator ShowDialog(Dialog dialog){
        //Set-up
        yield return new WaitForEndOfFrame();
        this.dialog = dialog;
        dialogState = true;
        dialogBox.SetActive(true);
        isComplete = false;
        currentLine = 0;

        
        yield return StartCoroutine(waitForFinish());

        //TODO avoid this line until all parts of dialog have been run
        //Completed with waitForFinish function (REVIEWED)
        yield return null;
    }

    public void ShowBatchDialog(Conversation conversation, int start, int numShown){
        //Max number of batch dialog is 6 entries
        if(conversation.Lines.Count >= numShown && conversation.Lines.Count >= start + numShown){
            batchAnswers.SetActive(true);
            numChoices = numShown;
            if(numChoices <= 6){

            //Set button text to dialog references and enable buttons
            for( int i = 0; i < numChoices; ++i){
                switch(i)
                {
                    case 0:
                    ansA.gameObject.SetActive(true);
                    ansTextA.text = conversation.Lines[start + i].Lines[0];
                    ansA.GetComponentInChildren<TextMeshProUGUI>().text = conversation.References[start + i];
                    break;

                    case 1:
                    ansB.gameObject.SetActive(true);
                    ansTextB.text = conversation.Lines[start + i].Lines[0];
                    ansB.GetComponentInChildren<TextMeshProUGUI>().text = conversation.References[start + i];
                    break;

                    case 2:
                    ansC.gameObject.SetActive(true);
                    ansTextC.text = conversation.Lines[start + i].Lines[0];
                    ansC.GetComponentInChildren<TextMeshProUGUI>().text = conversation.References[start + i];
                    break;

                    case 3:
                    ansD.gameObject.SetActive(true);
                    ansTextD.text = conversation.Lines[start + i].Lines[0];
                    ansD.GetComponentInChildren<TextMeshProUGUI>().text = conversation.References[start + i];
                    break;
                    
                    case 4:
                    ansE.gameObject.SetActive(true);
                    ansTextE.text = conversation.Lines[start + i].Lines[0];
                    ansE.GetComponentInChildren<TextMeshProUGUI>().text = conversation.References[start + i];
                    break;
                    
                    case 5:
                    ansF.gameObject.SetActive(true);
                    ansTextF.text = conversation.Lines[start + i].Lines[0];
                    ansF.GetComponentInChildren<TextMeshProUGUI>().text = conversation.References[start + i];
                    break;

                }
            }
            //Set unused buttons inactive 
            for(int j = numChoices; j < 6; ++j){
                switch(j)
                {
                    case 0:
                    ansA.gameObject.SetActive(false);
                    ansTextA.text = "";
                    break;

                    case 1:
                    ansB.gameObject.SetActive(false);
                    ansTextB.text ="";
                    break;

                    case 2:
                    ansC.gameObject.SetActive(false);
                    ansTextC.text = "";
                    break;

                    case 3:
                    ansD.gameObject.SetActive(false);
                    ansTextD.text = "";
                    break;

                    case 4:
                    ansE.gameObject.SetActive(false);
                    ansTextE.text = "";
                    break;

                    case 5:
                    ansF.gameObject.SetActive(false);
                    ansTextF.text = "";
                    break;
                }
            }
            }
        }
    }

//Coroutine
//Typing effect for generated text
    public IEnumerator TypeDialog(string dialog){
        isTyping = true;
        dialogText.text = "";
        foreach (var letter in dialog.ToCharArray()){
            dialogText.text += letter;
            yield return new WaitForSeconds(1f / lettersPerSecond);
        }
        
        isTyping = false;
        yield return null;
    }
    

//Used by controllers
//NOTE for Sunny: Handle replies here
    public void HandleUpdate(){
        //first cycle through all dialog until you hit last line,
        //then check if it is replyable or not
        //if not, end normally
        //if so wait for button to be pressed and trigger next action
        if(isComplete == false){
           if (Input.GetKeyDown(KeyCode.Space) || buttonChoice != -1){
            if(isTyping == false){
                ++currentLine;
                if(currentLine < dialog.Lines.Count){
                //Handles subsequent line display
                StartCoroutine(TypeDialog(dialog.Lines[currentLine]));
                }
                else if(choiceMade == true) {
                    currentLine = 0;
                    isComplete = true;
                    
                }
            }
            }

        }
    }

    public IEnumerator waitForFinish(){

        //Handles intial line display
        yield return StartCoroutine(TypeDialog(dialog.Lines[currentLine]));

        //Let HandleUpdate run through remaining code lines before ending coroutine
        while(isComplete == false){
            yield return new WaitForSeconds(1f/4f);
        }
        yield return null;
    }

    
    public void WaitForChoice(){
        choiceMade = false;
    }
    public void ChoiceSelected(){
        choiceMade = true;
    }

    public int ButtonChoice(){
        return buttonChoice;
    }

    public void choiceA(){
        buttonChoice = 0;
    }

    public void choiceB(){
        buttonChoice = 1;
    }

    public void choiceC(){
        buttonChoice = 2;
    }

    public void choiceD(){
        buttonChoice = 3;
    }

    public void choiceE(){
        buttonChoice = 4;
    }

    public void choiceF(){
        buttonChoice = 5;
    }

    public void resetChoice(){
        buttonChoice = -1;
    }


    public bool currDialogState(){
        return dialogState;
    }

    public void changeState(){
        dialogState  = false;
    }
}
