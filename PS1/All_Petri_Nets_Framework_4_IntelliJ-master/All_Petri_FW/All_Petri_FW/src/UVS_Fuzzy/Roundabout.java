package UVS_Fuzzy;

import Components.*;
import DataObjects.DataFuzzy;
import DataObjects.DataTransfer;
import DataOnly.*;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.util.ArrayList;

public class Roundabout {

    public static PetriNet createPetriNet() {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Roundabout";
        pn.NetworkPort = 1086;

        // --------------------------------------------------------------------
        // ----------------------- FLRS Tables --------------------------------
        // --------------------------------------------------------------------
        FLRS flrsMove = new FLRS(
                new FV(FZ.NL),
                new FV(FZ.NM),
                new FV(FZ.ZR),
                new FV(FZ.PM),
                new FV(FZ.PL)
        );

        FLRS flrsPlus = new FLRS(
                new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR),
                new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), new FV(FZ.PL),
                new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), new FV(FZ.PL), new FV(FZ.PL)
        );

        // --------------------------------------------------------------------
        // ----------------------- Constant Places ----------------------------
        // --------------------------------------------------------------------
        DataFuzzy c033 = new DataFuzzy();
        c033.SetName("c033");
        c033.SetValue(new Fuzzy(0.33F));
        c033.Printable = false;
        pn.ConstantPlaceList.add(c033);

        // --------------------------------------------------------------------
        // --------------------------- Places ---------------------------------
        // --------------------------------------------------------------------
        // Input places (P_i1 receives from Exit Lane 2; P_i2, P_i3 are roundabout arms)
        DataFuzzy pI1 = new DataFuzzy();
        pI1.SetName("P_i1");
        pn.PlaceList.add(pI1);

        DataFuzzy pI2 = new DataFuzzy();
        pI2.SetName("P_i2");
        pI2.SetValue(new Fuzzy(0.4F));
        pn.PlaceList.add(pI2);

        DataFuzzy pI3 = new DataFuzzy();
        pI3.SetName("P_i3");
        pI3.SetValue(new Fuzzy(0.3F));
        pn.PlaceList.add(pI3);

        // Roundabout segments
        DataFuzzy l1 = new DataFuzzy();
        l1.SetName("L1");
        pn.PlaceList.add(l1);

        DataFuzzy l2 = new DataFuzzy();
        l2.SetName("L2");
        pn.PlaceList.add(l2);

        DataFuzzy l3 = new DataFuzzy();
        l3.SetName("L3");
        pn.PlaceList.add(l3);

        // Summation places
        DataFuzzy l1l2 = new DataFuzzy();
        l1l2.SetName("L1L2");
        pn.PlaceList.add(l1l2);

        DataFuzzy l1l2l3 = new DataFuzzy();
        l1l2l3.SetName("L1L2L3");
        pn.PlaceList.add(l1l2l3);

        // Split places
        DataFuzzy e1 = new DataFuzzy();
        e1.SetName("E1");
        pn.PlaceList.add(e1);

        DataFuzzy e2 = new DataFuzzy();
        e2.SetName("E2");
        pn.PlaceList.add(e2);

        DataFuzzy e3 = new DataFuzzy();
        e3.SetName("E3");
        pn.PlaceList.add(e3);

        // Output places
        DataFuzzy pOut1 = new DataFuzzy();
        pOut1.SetName("P_out1");
        pn.PlaceList.add(pOut1);

        DataFuzzy pOut2 = new DataFuzzy();
        pOut2.SetName("P_out2");
        pn.PlaceList.add(pOut2);

        // P_out3: DataTransfer to Lane 2 (port 1082), place P_R
        DataTransfer pOut3 = new DataTransfer();
        pOut3.SetName("P_out3");
        pOut3.Value = new TransferOperation("localhost", "1082", "P_R");
        pn.PlaceList.add(pOut3);

        // --------------------------------------------------------------------
        // ------------------------- Transitions ------------------------------
        // --------------------------------------------------------------------
        // t1: P_i1 -> L1 (FLRS Move)
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "t1";
        t1.InputPlaceName.add("P_i1");
        Condition t1Ct = new Condition(t1, "P_i1", TransitionCondition.NotNull);
        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = t1Ct;
        ArrayList<PlaceNameWithWeight> inT1 = new ArrayList<>();
        inT1.add(new PlaceNameWithWeight("P_i1", 1.0F));
        ArrayList<String> outT1 = new ArrayList<>();
        outT1.add("L1");
        grdT1.Activations.add(new Activation(t1, flrsMove, inT1, TransitionOperation.FLRS, outT1));
        t1.GuardMappingList.add(grdT1);
        t1.Delay = 0;
        pn.Transitions.add(t1);

        // t2: P_i2 -> L2 (FLRS Move)
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "t2";
        t2.InputPlaceName.add("P_i2");
        Condition t2Ct = new Condition(t2, "P_i2", TransitionCondition.NotNull);
        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = t2Ct;
        ArrayList<PlaceNameWithWeight> inT2 = new ArrayList<>();
        inT2.add(new PlaceNameWithWeight("P_i2", 1.0F));
        ArrayList<String> outT2 = new ArrayList<>();
        outT2.add("L2");
        grdT2.Activations.add(new Activation(t2, flrsMove, inT2, TransitionOperation.FLRS, outT2));
        t2.GuardMappingList.add(grdT2);
        t2.Delay = 0;
        pn.Transitions.add(t2);

        // t3: P_i3 -> L3 (FLRS Move)
        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "t3";
        t3.InputPlaceName.add("P_i3");
        Condition t3Ct = new Condition(t3, "P_i3", TransitionCondition.NotNull);
        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = t3Ct;
        ArrayList<PlaceNameWithWeight> inT3 = new ArrayList<>();
        inT3.add(new PlaceNameWithWeight("P_i3", 1.0F));
        ArrayList<String> outT3 = new ArrayList<>();
        outT3.add("L3");
        grdT3.Activations.add(new Activation(t3, flrsMove, inT3, TransitionOperation.FLRS, outT3));
        t3.GuardMappingList.add(grdT3);
        t3.Delay = 0;
        pn.Transitions.add(t3);

        // t4: L1 + L2 -> L1L2 (FLRS +)
        PetriTransition t4 = new PetriTransition(pn);
        t4.TransitionName = "t4";
        t4.InputPlaceName.add("L1");
        t4.InputPlaceName.add("L2");
        Condition t4Ct1 = new Condition(t4, "L1", TransitionCondition.NotNull);
        Condition t4Ct2 = new Condition(t4, "L2", TransitionCondition.NotNull);
        t4Ct1.SetNextCondition(LogicConnector.AND, t4Ct2);
        GuardMapping grdT4 = new GuardMapping();
        grdT4.condition = t4Ct1;
        ArrayList<PlaceNameWithWeight> inT4 = new ArrayList<>();
        inT4.add(new PlaceNameWithWeight("L1", 1.0F));
        inT4.add(new PlaceNameWithWeight("L2", 1.0F));
        ArrayList<String> outT4 = new ArrayList<>();
        outT4.add("L1L2");
        grdT4.Activations.add(new Activation(t4, flrsPlus, inT4, TransitionOperation.FLRS, outT4));
        t4.GuardMappingList.add(grdT4);
        t4.Delay = 0;
        pn.Transitions.add(t4);

        // t5: L1L2 + L3 -> L1L2L3 (FLRS +)
        PetriTransition t5 = new PetriTransition(pn);
        t5.TransitionName = "t5";
        t5.InputPlaceName.add("L1L2");
        t5.InputPlaceName.add("L3");
        Condition t5Ct1 = new Condition(t5, "L1L2", TransitionCondition.NotNull);
        Condition t5Ct2 = new Condition(t5, "L3", TransitionCondition.NotNull);
        t5Ct1.SetNextCondition(LogicConnector.AND, t5Ct2);
        GuardMapping grdT5 = new GuardMapping();
        grdT5.condition = t5Ct1;
        ArrayList<PlaceNameWithWeight> inT5 = new ArrayList<>();
        inT5.add(new PlaceNameWithWeight("L1L2", 1.0F));
        inT5.add(new PlaceNameWithWeight("L3", 1.0F));
        ArrayList<String> outT5 = new ArrayList<>();
        outT5.add("L1L2L3");
        grdT5.Activations.add(new Activation(t5, flrsPlus, inT5, TransitionOperation.FLRS, outT5));
        t5.GuardMappingList.add(grdT5);
        t5.Delay = 0;
        pn.Transitions.add(t5);

        // T_s: L1L2L3 -> E1, E2, E3 (Prod_Fuzzy with 0.33)
        PetriTransition tS = new PetriTransition(pn);
        tS.TransitionName = "T_s";
        tS.InputPlaceName.add("L1L2L3");
        Condition tSCt = new Condition(tS, "L1L2L3", TransitionCondition.NotNull);
        GuardMapping grdTS = new GuardMapping();
        grdTS.condition = tSCt;

        ArrayList<PlaceNameWithWeight> inS1 = new ArrayList<>();
        inS1.add(new PlaceNameWithWeight("L1L2L3", 1.0F));
        inS1.add(new PlaceNameWithWeight("c033", 1.0F));
        grdTS.Activations.add(new Activation(inS1, TransitionOperation.Prod_Fuzzy, "E1", tS));

        ArrayList<PlaceNameWithWeight> inS2 = new ArrayList<>();
        inS2.add(new PlaceNameWithWeight("L1L2L3", 1.0F));
        inS2.add(new PlaceNameWithWeight("c033", 1.0F));
        grdTS.Activations.add(new Activation(inS2, TransitionOperation.Prod_Fuzzy, "E2", tS));

        ArrayList<PlaceNameWithWeight> inS3 = new ArrayList<>();
        inS3.add(new PlaceNameWithWeight("L1L2L3", 1.0F));
        inS3.add(new PlaceNameWithWeight("c033", 1.0F));
        grdTS.Activations.add(new Activation(inS3, TransitionOperation.Prod_Fuzzy, "E3", tS));

        tS.GuardMappingList.add(grdTS);
        tS.Delay = 0;
        pn.Transitions.add(tS);

        // t6 (t5 in diagram): E1 -> P_out1 (Move)
        PetriTransition t6 = new PetriTransition(pn);
        t6.TransitionName = "t6";
        t6.InputPlaceName.add("E1");
        Condition t6Ct = new Condition(t6, "E1", TransitionCondition.NotNull);
        GuardMapping grdT6 = new GuardMapping();
        grdT6.condition = t6Ct;
        grdT6.Activations.add(new Activation(t6, "E1", TransitionOperation.Move, "P_out1"));
        t6.GuardMappingList.add(grdT6);
        t6.Delay = 0;
        pn.Transitions.add(t6);

        // t7 (t6 in diagram): E2 -> P_out2 (Move)
        PetriTransition t7 = new PetriTransition(pn);
        t7.TransitionName = "t7";
        t7.InputPlaceName.add("E2");
        Condition t7Ct = new Condition(t7, "E2", TransitionCondition.NotNull);
        GuardMapping grdT7 = new GuardMapping();
        grdT7.condition = t7Ct;
        grdT7.Activations.add(new Activation(t7, "E2", TransitionOperation.Move, "P_out2"));
        t7.GuardMappingList.add(grdT7);
        t7.Delay = 0;
        pn.Transitions.add(t7);

        // t8 (t7 in diagram): E3 -> P_out3 (SendOverNetwork to Lane 2, P_R)
        PetriTransition t8 = new PetriTransition(pn);
        t8.TransitionName = "t8";
        t8.InputPlaceName.add("E3");
        Condition t8Ct = new Condition(t8, "E3", TransitionCondition.NotNull);
        GuardMapping grdT8 = new GuardMapping();
        grdT8.condition = t8Ct;
        grdT8.Activations.add(new Activation(t8, "E3", TransitionOperation.SendOverNetwork, "P_out3"));
        t8.GuardMappingList.add(grdT8);
        t8.Delay = 0;
        pn.Transitions.add(t8);

        return pn;
    }

    public static void main(String[] args) {
        PetriNet pn = createPetriNet();

        System.out.println("Roundabout started \n ------------------------------");
        pn.Delay = 2000;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}