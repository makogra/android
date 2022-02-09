package com.mako.heroslandidle;

import android.content.res.Resources;

import java.util.Arrays;
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
        private static final float RES_BOUND = 8.0F;
        private static final int RES_MINIMUM = 16;
        
        // "res" stands for resources like gold, iron wood ...
        private String resType;
        private int resAmount;
        private String[] resTypes;
        private int resTypeIndex;

        public String getResType() {
            return resType;
        }

        public int getResAmount() {
            return resAmount;
        }

        public int getResTypeIndex(){
            return resTypeIndex;
        }

        public void setResAmount(int amount){
            if(amount < resAmount && amount >= 0)
                resAmount = amount;
        }

        ResourcesField(){
            resTypes = res.getStringArray(R.array.resources_arr);
            System.out.println("resTypes = " + Arrays.toString(resTypes));
            resTypeIndex = generateResTypeIndex();
            resType = resTypes[resTypeIndex];
            resAmount = generateResAmount();
            
            //TODO clear sout
            System.out.println("resType = " + resType);
            System.out.println("resAmount = " + resAmount);
            System.out.println("BIOM = " + BIOM);
        }
        
        private int generateResTypeIndex(){
            Random random = new Random();
            float temp = resTypes.length / (float) BIOMS_LENGTH;
            System.out.println("BIOM_INDEX = " + BIOM_INDEX);
            System.out.println("resTypes.length = " + resTypes.length);
            System.out.println("temp = " + temp);
            int result = (int)((temp * random.nextGaussian()) + (temp * (BIOM_INDEX+1))) % resTypes.length;
            result = Math.abs(result);
            System.out.println("result = " + result);
            return result;
        }
        
        private int generateResAmount(){
            Random random = new Random();
            return (int) (RES_BOUND * random.nextGaussian() + RES_BOUND) + RES_MINIMUM;
        }
    }

    Land(Resources res){
        this.res = res;

        Random random = new Random();
        String[] bioms = res.getStringArray(R.array.land_types);
        System.out.println("bioms = " + Arrays.toString(bioms));
        System.out.println("bioms = " + bioms.length);
        BIOMS_LENGTH = bioms.length;
        BIOM_INDEX = random.nextInt(bioms.length);
        BIOM = bioms[BIOM_INDEX];
        System.out.println("BIOMS_LENGTH = " + BIOMS_LENGTH);
        System.out.println("random = " + random.nextInt(BIOMS_LENGTH));

        leftResField = new ResourcesField();
        rightResField = new ResourcesField();
    }
}
