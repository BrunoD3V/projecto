/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

/**
 *
 * @author bruno
 */
public class NodeGest {
    
    private float TempMin;
    private float TempMax;
    private float HumiMin;
    private float HumiMax;
    private float RadiMin;
    private float RadiMax;
    private String sector;

    //Construtor
    public NodeGest(float TempMin, float TempMax, float HumiMin, float HumiMax, float RadiMin, float RadiMax, String sector) {
        this.TempMin = TempMin;
        this.TempMax = TempMax;
        this.HumiMin = HumiMin;
        this.HumiMax = HumiMax;
        this.RadiMin = RadiMin;
        this.RadiMax = RadiMax;
        this.sector = sector;
    }
    
    public NodeGest(){
        this.sector = "";
    }


    //Getters e Setters
    public float getTempMin() {
        return TempMin;
    }

    public void setTempMin(float TempMin) {
        this.TempMin = TempMin;
    }

    public float getTempMax() {
        return TempMax;
    }

    public void setTempMax(float TempMax) {
        this.TempMax = TempMax;
    }

    public float getHumiMin() {
        return HumiMin;
    }

    public void setHumiMin(float HumiMin) {
        this.HumiMin = HumiMin;
    }

    public float getHumiMax() {
        return HumiMax;
    }

    public void setHumiMax(float HumiMax) {
        this.HumiMax = HumiMax;
    }

    public float getRadiMin() {
        return RadiMin;
    }

    public void setRadiMin(float RadiMin) {
        this.RadiMin = RadiMin;
    }

    public float getRadiMax() {
        return RadiMax;
    }

    public void setRadiMax(float RadiMax) {
        this.RadiMax = RadiMax;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
