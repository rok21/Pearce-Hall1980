package Models;

import Constants.DefaultValuesConstants;
import Models.Parameters.CsParameter;
import Models.Parameters.InitialAlphaParameter;
import Models.Parameters.SalienceExcitatoryParameter;
import Models.Parameters.SalienceInhibitoryParameter;

import java.util.ArrayList;

/**
 * Created by Rokas on 03/11/2015.
 */
public class ConditionalStimulus {

    public InitialAlphaParameter InitialAlphaParameter;
    public SalienceExcitatoryParameter SalienceExcitatoryParameter;
    public SalienceInhibitoryParameter SalienceInhibitoryParameter;

    public char Name;

    private double associationInhibitory;
    private double associationExcitatory;

    private boolean alphaSet;
    private double alpha;

    public ConditionalStimulus(char name, InitialAlphaParameter initialAlphaParameter, SalienceExcitatoryParameter salienceExcitatoryParameter, SalienceInhibitoryParameter salienceInhibitoryParameter){
        Name = name;

        associationExcitatory = 0;
        associationInhibitory = 0;
        alphaSet = false;

        InitialAlphaParameter = initialAlphaParameter;
        SalienceExcitatoryParameter = salienceExcitatoryParameter;
        SalienceInhibitoryParameter = salienceInhibitoryParameter;
    }

    public ConditionalStimulus(char name, InitialAlphaParameter initialAlphaParameter,
                               SalienceExcitatoryParameter salienceExcitatoryParameter,
                               SalienceInhibitoryParameter salienceInhibitoryParameter,
                               double associationExcitatory,
                               double associationInhibitory,
                               double alpha) {
        this(name, initialAlphaParameter, salienceExcitatoryParameter, salienceInhibitoryParameter);
        this.associationExcitatory = associationExcitatory;
        this.associationInhibitory = associationInhibitory;
        setAlpha(alpha);
    }

    public void reset(ConditionalStimulus cs){
        associationExcitatory = cs.getAssociationExcitatory();
        associationInhibitory = cs.getAssociationInhibitory();
        setAlpha(cs.getAlpha());
    }

    public double getAssociationNet() {
        return Math.min(associationExcitatory - associationInhibitory, DefaultValuesConstants.ASSOCIATION_ASYMPTOTE);
    }

    public double getAlpha(){
        return alphaSet ? alpha : InitialAlphaParameter.getValue();
    }

    public void setAlpha(double value){
        alpha = value;
        alphaSet = true;
    }

    public double getAssociationExcitatory(){
        return Math.min(associationExcitatory, DefaultValuesConstants.ASSOCIATION_ASYMPTOTE);
    }

    public double getAssociationInhibitory(){
        return Math.min(associationInhibitory, DefaultValuesConstants.ASSOCIATION_ASYMPTOTE);
    }

    public void updateAssociationExcitatory(double change){
        associationExcitatory+=change;
    }

    public void updateAssociationInhibitory(double change){
        associationInhibitory+=change;
    }

    public void setAssociationExcitatory(double newVal){
        associationExcitatory=newVal;
    }

    public void setAssociationInhibitory(double newVal){
        associationInhibitory=newVal;
    }

    public ConditionalStimulus getCopy(){
        return new ConditionalStimulus(Name, InitialAlphaParameter, SalienceExcitatoryParameter, SalienceInhibitoryParameter, associationExcitatory, associationInhibitory, getAlpha());
    }
}