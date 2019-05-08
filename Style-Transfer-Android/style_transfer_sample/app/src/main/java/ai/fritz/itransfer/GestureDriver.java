package ai.fritz.itransfer;

public class GestureDriver {

    public static boolean isVerticalSwipe(float deltaX, float deltaY){
        return  (Math.abs(deltaY) > 80) &&
                (Math.abs(deltaY) - Math.abs(deltaX) > 40);
    }

    public static boolean isSwipeUp(float deltaY){
        return deltaY >= 0;
    }

    public static boolean isSwipeDown(float deltaY){
        return deltaY < 0;
    }

    public static boolean isGorizontalSwipe(float deltaX, float deltaY){
        return  (Math.abs(deltaX) > 80) &&
                (Math.abs(deltaX) - Math.abs(deltaY) > 40);
    }

    public static boolean isSwipeRight(float deltaX){
        return deltaX > 0;
    }

    public static boolean isSwipeLeft(float deltaX){
        return deltaX <= 0;
    }
}

