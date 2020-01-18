package logic;

import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMap;

import java.util.HashMap;
import java.util.Map;

public class TileMapInfo {

    // the map itself
    public static TiledMap map;

    // tile size info
    public static int TILE_WIDTH, TILE_HEIGHT, LEVEL_WIDTH_TILES, LEVEL_HEIGHT_TILES, LEVEL_WIDTH_PIXELS,
            LEVEL_HEIGHT_PIXELS;

    // tilesets
    public static final int ENEMY_TILES_TILESET_IDX = 0;
    public static final int LANDSCAPE_TILES_TILESET_IDX = 1;
    public static final int PLANE_TILES_TILESET_IDX = 2;

    // layers
    public static final int LANDSCAPE_TILES_LAYER_IDX = 0;
    public static final int ENEMY_TILES_LAYER_IDX = 1;
    public static final int DESTRUCTION_TILES_LAYER_IDX = 2;

    public static int[] CLASS_GRASS, CLASS_CONCRETE, CLASS_DIRT;

    public static int[] staticWarAttender_indices;

    public static final int[] windmill_indices;
    public static final int[] static_plane_creation_indices;
    public static final int[] static_plane_collision_indices;
    public static final int[] collision_replace_indices;
    public static final int[] destructible_tile_indices;
    public static final int[] destructible_tile_replace_indices;
    public static final int[] indestructible_tile_indices;

    public static Map<Integer, Float> destructible_tiles_health_info;

    static {
        windmill_indices = new int[]{0, 1, 2};
        static_plane_creation_indices = new int[]{  // indices for creation of big static plane
                22, // the plane that's facing right in the tileset
                27, // the plane that's facing down in the tileset
                72, // the plane that's facing left in the tileset
                77  // the plane that's facing up in the tileset
        };
        static_plane_collision_indices = new int[]{ // indices for collision with big static plane
                12, 21, 22, 23, 32,  // the plane that's facing right in the tileset
                17, 26, 27, 28, 37,  // the plane that's facing down in the tileset
                62, 71, 72, 73, 82,  // the plane that's facing left in the tileset
                67, 76, 77, 78, 87   // the plane that's facing up in the tileset
        };
        collision_replace_indices = new int[]{
                96, // DIRT
                97, // CONCRETE
                98 // GRASS
        };
        destructible_tile_indices = new int[]{1, 2, 18, 19, 25, 65, 68, 83, 88, 89};
        destructible_tile_replace_indices = new int[]{32, 33, 34, 35, 36, 37, 95, 94, 93, 91};
        indestructible_tile_indices = new int[]{40, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 64, 66, 67,
                68, 72, 73, 74, 75, 76, 77};
    }

    public static void init() {
        TILE_WIDTH = map.getTileWidth();
        TILE_HEIGHT = map.getTileHeight();
        LEVEL_WIDTH_TILES = map.getWidth();
        LEVEL_HEIGHT_TILES = map.getHeight();
        LEVEL_WIDTH_PIXELS = LEVEL_WIDTH_TILES * TILE_WIDTH;
        LEVEL_HEIGHT_PIXELS = LEVEL_HEIGHT_TILES * TILE_HEIGHT;

        // create TileInfo for 'landscape_tiles' TileSet
        TileSet landscape_tiles = map.getTileSet(LANDSCAPE_TILES_TILESET_IDX);
        if (!landscape_tiles.name.equals("landscape_tiles"))
            throw new IllegalAccessError(
                    "Wrong tileset index: [" + LANDSCAPE_TILES_TILESET_IDX + "] is not landscape_tiles");
        else {
            int idx;
            for (idx = 0; idx < destructible_tile_indices.length; ++idx) {
                destructible_tile_indices[idx] += landscape_tiles.firstGID;
                destructible_tile_replace_indices[idx] += landscape_tiles.firstGID;
            }
            for (idx = 0; idx < indestructible_tile_indices.length; ++idx) {
                indestructible_tile_indices[idx] += landscape_tiles.firstGID;
            }
            for (idx = 0; idx < collision_replace_indices.length; ++idx) {
                collision_replace_indices[idx] += landscape_tiles.firstGID;
            }
        }

        // init tiles of grass for tile replacement
        int[] grass_indices = new int[]{
                0, 3, 8, 20, 21, 22, 23
        };
        CLASS_GRASS = new int[grass_indices.length];
        for (int i = 0; i < grass_indices.length; ++i) {
            CLASS_GRASS[i] = landscape_tiles.firstGID + grass_indices[i];
        }
        // init tiles of concrete for tile replacement
        int[] concrete_indices = new int[]{
                62, 63, 70, 71, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 90
        };
        CLASS_CONCRETE = new int[concrete_indices.length];
        for (int i = 0; i < concrete_indices.length; ++i) {
            CLASS_CONCRETE[i] = landscape_tiles.firstGID + concrete_indices[i];
        }
        // init tiles of dirt for tile replacement
        int[] dirt_indices = new int[]{
                4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 27, 28, 29
        };
        CLASS_DIRT = new int[dirt_indices.length];
        for (int i = 0; i < dirt_indices.length; ++i) {
            CLASS_DIRT[i] = landscape_tiles.firstGID + dirt_indices[i];
        }

        // create TileInfo for 'enemy_tiles' TileSet
        TileSet enemy_tiles = map.getTileSet(ENEMY_TILES_TILESET_IDX);
        if (!enemy_tiles.name.equals("enemy_tiles"))
            throw new IllegalAccessError("Wrong tileset index: [" + ENEMY_TILES_TILESET_IDX + "] is not enemy_tiles");
        else {
            for (int idx = 0; idx < windmill_indices.length; ++idx) {
                windmill_indices[idx] += enemy_tiles.firstGID;
            }
        }

        // create TileInfo for 'plane_tiles' TileSet
        TileSet plane_tiles = map.getTileSet(PLANE_TILES_TILESET_IDX);
        if (!plane_tiles.name.equals("plane_tiles"))
            throw new IllegalAccessError("Wrong tileset index: [" + PLANE_TILES_TILESET_IDX + "] is not plane_tiles");
        else {
            for (int idx = 0; idx < static_plane_creation_indices.length; ++idx) {
                static_plane_creation_indices[idx] += plane_tiles.firstGID;
            }
            for (int idx = 0; idx < static_plane_collision_indices.length; ++idx) {
                static_plane_collision_indices[idx] += plane_tiles.firstGID;
            }
        }

        staticWarAttender_indices = new int[static_plane_collision_indices.length + windmill_indices.length];
        for (int i = 0; i < staticWarAttender_indices.length; ++i) {
            if (i < static_plane_collision_indices.length)
                staticWarAttender_indices[i] = static_plane_collision_indices[i];
            else staticWarAttender_indices[i] = windmill_indices[i - static_plane_collision_indices.length];
        }

        destructible_tiles_health_info = new HashMap<>();
    }

    public static void reset() {
        if (destructible_tiles_health_info == null) destructible_tiles_health_info = new HashMap<>();
        else destructible_tiles_health_info.clear();
    }

    public static void setMap(TiledMap myMap) {
        map = myMap;
    }

    public static int getReplacementTileID(int tileID) {
        for (int value : CLASS_DIRT) {
            if (tileID == value) return collision_replace_indices[0];
        }
        for (int value : CLASS_CONCRETE) {
            if (tileID == value) return collision_replace_indices[1];
        }
        for (int value : CLASS_GRASS) {
            if (tileID == value) return collision_replace_indices[2];
        }
        return collision_replace_indices[2];    // return grass by default
    }

    public static boolean isCollisionTile(int tileID) {
        for (int value : indestructible_tile_indices) {
            if (tileID == value) return true;
        }
        for (int value : destructible_tile_indices) {
            if (tileID == value) return true;
        }
        return false;
    }

}
