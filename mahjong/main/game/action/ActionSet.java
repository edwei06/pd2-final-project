package mahjong.main.game.action;

import java.util.ArrayList;

public class ActionSet {
    public ArrayList<Action> avaliableActions = new ArrayList<Action>();
    public Action chosenAction;
    
    public ArrayList<Action> getAvaliableAcitons(){
        return avaliableActions;
    }

    public Action getChosenAction(){
        return chosenAction;
    }

    @Override
    public String toString(){
        return "ActionSet [avaliableActions =" + avaliableActions + ", chosenAction ="+ chosenAction + "]";
    }
}
