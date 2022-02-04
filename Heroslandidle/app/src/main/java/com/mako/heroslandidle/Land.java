package com.mako.heroslandidle;

import android.content.res.Resources;

import java.util.Random;

public class Land {

    // biom ex. Woods, Mountains, Swamp 
    private final int BIOMS_LENGTH;
    private final String BIOM;
    private final int BIOM_INDEX;
    private final ResourcesField leftResField;
    private final ResourcesField rightResField;
    private final Resources res;

    public String getBiom() {
        return BIOM;
    }

    public ResourcesField getLeftResField() {
        return leftResField;
    }

    public ResourcesField getRightResField() {
        return rightResField;
    }


    class ResourcesField{
        
        //Config
        private static final float RES_BOUND = 16.0F;
        private static final int RES_MINIMUM = 16;
        
        // "res" stands for resources like gold, iron wood ...
        private String resType;
        private int resAmount;
        private String[] resTypes;

        public String getResType() {
            return resType;
        }

        public int getResAmount() {
            return resAmount;
        }

        ResourcesField(){
            resTypes = res.getStringArray(R.array.resourcesArr);
            resType = resTypes[generateResTypeIndex()];
            resAmount = generateResAmount();
            
            //TODO clear
            System.out.println("resType = " + resType);
            System.out.println("resAmount = " + resAmount);
            System.out.println("BIOM = " + BIOM);
        }
        
        private int generateResTypeIndex(){
            Random random = new Random();
            float temp = (float) (resTypes.length * (BIOM_INDEX + 1) / BIOMS_LENGTH);

            return (int)((temp * random.nextGaussian() / 2) + 2 * temp) % resTypes.length;
        }
        
        private int generateResAmount(){
            Random random = new Random();
            return (int) (RES_BOUND * random.nextGaussian()/2 + RES_BOUND) + RES_MINIMUM;
        }
    }

    Land(Resources res){
        this.res = res;

        Random random = new Random();
        String[] bioms = res.getStringArray(R.array.land_types);
        BIOMS_LENGTH = bioms.length;
        BIOM_INDEX = random.nextInt(bioms.length);
        BIOM = bioms[BIOM_INDEX];

        leftResField = new ResourcesField();
        rightResField = new ResourcesField();
    }
}
