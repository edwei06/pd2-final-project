package mahjong.main.game.action;

import java.io.Serializable;
import java.util.ArrayList;

public class ActionSet implements Serializable{
    public ArrayList<Action> avaliableActions = new ArrayList<Action>(); //能夠做的指令
    public Action chosenAction; //選擇要做的指令
    
    public ArrayList<Action> getAvaliableAcitons(){
        return avaliableActions;
    }

    public Action getChosenAction(){
        return chosenAction;
    }

    public void setChosenAction(Action action){
        this.chosenAction = action;
    }

    public void avaliableActionsClaer(){
        this.avaliableActions.clear();
    }

    public void chosenActionClear(){
        this.chosenAction = null;
    }

    @Override
    public String toString(){
        return "ActionSet [avaliableActions =" + avaliableActions + ", chosenAction ="+ chosenAction + "]";
    }
}
