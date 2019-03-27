package com.darkpixellabyrinth.dpl.Levels;

import android.content.Context;

import com.darkpixellabyrinth.dpl.Direction;
import com.darkpixellabyrinth.dpl.Intersection;
import com.darkpixellabyrinth.dpl.Level;
import com.darkpixellabyrinth.dpl.PathBranch;
import com.darkpixellabyrinth.dpl.Position;
import com.darkpixellabyrinth.dpl.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.darkpixellabyrinth.dpl.Constants.MAP_SIZE;

public class MapReader extends Level {

    private int[][] map;
    private int branchCount;
    private Context context;

    public MapReader(Context context) {

        this.context = context;
        this.chargerCsv();
        ArrayList<PathBranch> pathBranches = new ArrayList<>();
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int k = 0; k < MAP_SIZE; k++) {
                int caseValue = this.map[i][k];
                switch (caseValue) {
                    case 0:
                        // there is nothing on this case
                        // so nothing to do
                        break;
                    case 1:
                        System.out.println("ERROR");
                        break;
                    case 2:
                        // found the start of a branch
                        Direction direction = checkDirection(i, k);
                        int pathLength = checkPathLength(i, k, direction);
                        pathBranches.add(new PathBranch(this.context, pathLength, new Position(this.context,i, k), direction));
                        System.out.println(" yolo " + pathBranches);
                        break;
                    case 3:
                        // this is an intersection between 2 paths

                        break;
                    case 4:

                        break;


                }
            }
        }


    }

    private int checkPathLength(int i, int k, Direction direction) {
        int pathLength = 1;
        boolean endPathNotFound = true;
        switch (direction) {
            case RIGHT:
                while (endPathNotFound) {
                    if (this.map[i][k + pathLength] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
            case LEFT:
                while (endPathNotFound) {
                    if (this.map[i][k - pathLength] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
            case UP:
                while (endPathNotFound) {
                    if (this.map[i + pathLength][k] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
            case DOWN:
                while (endPathNotFound) {
                    if (this.map[i - pathLength][k + pathLength] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
        }
        return pathLength;
    }

    private Direction checkDirection(int i, int k) {
        Direction direction;
        if (this.map[i][k + 1] != 0) {
            direction = Direction.RIGHT;
        } else {
            if (this.map[i][k - 1] == 0) {
                direction = Direction.LEFT;
            } else {
                if (this.map[i + 1][k] == 0) {
                    direction = Direction.UP;
                } else {
                    direction = Direction.DOWN;
                }
            }
        }
        return direction;
    }

    public void addLineToTheMatrice(String ligne, int lineNumber) {
        String line[] = ligne.split(";");
        for (int columnNumber = 0; columnNumber < MAP_SIZE; columnNumber++) {
            if (columnNumber < line.length) {
                this.map[columnNumber][lineNumber] = Integer.valueOf(line[columnNumber]);
            } else {
                this.map[columnNumber][lineNumber] = 0;
            }
        }
    }

    public void chargerCsv() {
        try {
            InputStream flux = context.getAssets().open("level1.csv");
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            this.map = new int[MAP_SIZE][MAP_SIZE];
            int k = 0;
            while ((ligne = buff.readLine()) != null) {
                this.addLineToTheMatrice(ligne, k);
                k++;
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
