package quest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
public class Quest {
    private int currentStep=0;
    private boolean finished=false;
    private Map<Integer,String> steps=new HashMap<>();

    public Quest(){
        steps.put(0,"You woke up in the forest. what will you do?");
        steps.put(1,"You are walking through the forest and you see a cave. Go inside?");
        steps.put(2,"You are in a cave. There are two tunnels in front of you. Where will you go?");
    }
    public void nextStep(String answer){
        if(currentStep<steps.size()){
            currentStep++;
        }else {
            finished=true;
        }
    }

}
