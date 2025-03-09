package quest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerProgress implements Serializable {
    private static final long serialVersionUID = 2906642554793891381L;
    private String playerName;
    private int currentStep;
    private int gamesPlayed;
}
