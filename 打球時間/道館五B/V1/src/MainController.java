
public class MainController {
    private KeyBoard keyBoard;
    private Tank tank;
    private Telecom telecom;

    public MainController(Tank tank,Telecom telecom,KeyBoard keyBoard){
        this.tank = tank;
        this.telecom = telecom;
        this.keyBoard = keyBoard;
    }

    void pressButton(char key){
        if(key == 'f'){
            tank.moveForward();
        }
        else if(key == 'd'){
            telecom.disconnect();
        }
        else if(key == 'r'){
            // reset
        }
    }
}
