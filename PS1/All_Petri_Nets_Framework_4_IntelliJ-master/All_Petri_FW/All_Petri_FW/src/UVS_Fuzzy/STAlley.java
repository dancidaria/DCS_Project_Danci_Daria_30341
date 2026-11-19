package UVS_Fuzzy;

import Components.*;
import DataObjects.DataFuzzy;
import DataObjects.DataTransfer;
import DataOnly.Fuzzy;
import DataOnly.TransferOperation;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class STAlley {

    public static PetriNet createPetriNet() {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "ST Alley";
        pn.NetworkPort = 1087;

        // Input place P_i (sample initial flow; can also be injected manually during simulation)
        DataFuzzy pI = new DataFuzzy();
        pI.SetName("P_i");
        pI.SetValue(new Fuzzy(0.3F));
        pn.PlaceList.add(pI);

        // Output DataTransfer place P_out -> Lane 2 (port 1082), place P_st
        DataTransfer pOut = new DataTransfer();
        pOut.SetName("P_out");
        pOut.Value = new TransferOperation("localhost", "1082", "P_st");
        pn.PlaceList.add(pOut);

        // Transition t_send: P_i -> P_out (SendOverNetwork)
        PetriTransition tSend = new PetriTransition(pn);
        tSend.TransitionName = "t_send";
        tSend.InputPlaceName.add("P_i");

        Condition tSendCt = new Condition(tSend, "P_i", TransitionCondition.NotNull);
        GuardMapping grdTSend = new GuardMapping();
        grdTSend.condition = tSendCt;
        grdTSend.Activations.add(new Activation(tSend, "P_i", TransitionOperation.SendOverNetwork, "P_out"));
        tSend.GuardMappingList.add(grdTSend);
        tSend.Delay = 0;
        pn.Transitions.add(tSend);

        return pn;
    }

    public static void main(String[] args) {
        PetriNet pn = createPetriNet();

        System.out.println("ST Alley started \n ------------------------------");
        pn.Delay = 2000;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}