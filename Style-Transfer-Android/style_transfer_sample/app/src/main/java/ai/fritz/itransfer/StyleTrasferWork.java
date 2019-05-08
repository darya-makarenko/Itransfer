package ai.fritz.itransfer;

import ai.fritz.fritzvisionstylemodel.ArtisticStyle;


public class StyleTrasferWork {




    public static ArtisticStyle getArtisticStyle(int numStyle){
        ArtisticStyle style = ArtisticStyle.BICENTENNIAL_PRINT;
        switch (numStyle){
            case (0):
                style = ArtisticStyle.BICENTENNIAL_PRINT;
                break;
            case (1):
                style = ArtisticStyle.FEMMES;
                break;
            case (2):
                style = ArtisticStyle.HEAD_OF_CLOWN;
                break;
            case (3):
                style = ArtisticStyle.HORSES_ON_SEASHORE;
                break;
            case (4):
                style = ArtisticStyle.KALEIDOSCOPE;
                break;
            case (5):
                style = ArtisticStyle.PINK_BLUE_RHOMBUS;
                break;
            case (6):
                style = ArtisticStyle.POPPY_FIELD;
                break;
            case (7):
                style = ArtisticStyle.RITMO_PLASTICO;
                break;
            case (8):
                style = ArtisticStyle.STARRY_NIGHT;
                break;
            case (9):
                style = ArtisticStyle.THE_SCREAM;
                break;
            case (10):
                style = ArtisticStyle.THE_TRAIL;
                break;
        }
        return style;
    }



    public static int getStyleFromIndex(int picNumber){
        int picture = R.drawable.lichtenstein_bicentennial;
        switch (picNumber){
            case (0):
                picture = R.drawable.lichtenstein_bicentennial;
                break;
            case (1):
                picture = R.drawable.picasso_femmes;
                break;
            case (2):
                picture = R.drawable.kutter_clown;
                break;
            case (3):
                picture = R.drawable.horses_on_seashore;
                break;
            case (4):
                picture = R.drawable.afremov_caleydoscope;
                break;
            case (5):
                picture = R.drawable.rhombus;
                break;
            case (6):
                picture = R.drawable.monet_poppy_field;
                break;
            case (7):
                picture = R.drawable.severini_ritmo_plastico;
                break;
            case (8):
                picture = R.drawable.van_gogh_starry_night;
                break;
            case (9):
                picture = R.drawable.munch_the_scream;
                break;
            case (10):
                picture = R.drawable.nolan_the_trial;
                break;
        }
        return picture;
    }
}
