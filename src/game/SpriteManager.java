package game;

import java.util.*;


/*
This class is pretty much copied from https://carlfx.wordpress.com/2012/04/09/javafx-2-gametutorial-part-2/
 */
public class SpriteManager {

    private final static List<Sprite> GAME_ACTORS = new ArrayList<>();
    private final static List<Sprite> CHECK_COLLISION_LIST = new ArrayList<>();
    private final static Set<Sprite> CLEAN_UP_SPRITES = new HashSet<>();

    public List<Sprite> getAllSprites() {
        return GAME_ACTORS;
    }

    public Set<Sprite> getCleanUpSprites() {
        return CLEAN_UP_SPRITES;
    }

    public List<Sprite> getCollisionList() {
        return CHECK_COLLISION_LIST;
    }

    public void addSprites(Sprite ... sprites) {
        GAME_ACTORS.addAll(Arrays.asList(sprites));
    }

    public void removeSprites(Sprite ... sprites) {
        GAME_ACTORS.removeAll(Arrays.asList(sprites));
    }

    public void addSpritesToBeRemoved(Sprite... sprites) {
        CLEAN_UP_SPRITES.addAll(Arrays.asList(sprites));
    }

    public void resetCollisionsToCheck() {
        CHECK_COLLISION_LIST.clear();
        CHECK_COLLISION_LIST.addAll(GAME_ACTORS);
    }

    public void cleanupSprites() {
        GAME_ACTORS.removeAll(CLEAN_UP_SPRITES);
        CLEAN_UP_SPRITES.clear();
    }

}
