package com.darkpixellabyrinth.dpl.Levels;

import android.content.Context;
import android.content.SharedPreferences;

import com.darkpixellabyrinth.dpl.Constants;
import com.darkpixellabyrinth.dpl.Direction;
import com.darkpixellabyrinth.dpl.Intersection;
import com.darkpixellabyrinth.dpl.Level;
import com.darkpixellabyrinth.dpl.PathBranch;
import com.darkpixellabyrinth.dpl.Position;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.darkpixellabyrinth.dpl.Constants.MAP_SIZE;
import static com.darkpixellabyrinth.dpl.Constants.USER_DATA;
import static com.darkpixellabyrinth.dpl.Direction.DOWN;
import static com.darkpixellabyrinth.dpl.Direction.LEFT;
import static com.darkpixellabyrinth.dpl.Direction.RIGHT;
import static com.darkpixellabyrinth.dpl.Direction.UP;

public class MapReader extends Level {

    private int[][] map;
    private int branchCount;
    private Context context;

    public MapReader(Context context) {
        super(context);

        this.context = context;
        this.chargerCsv();
        ArrayList<PathBranch> pathBranches = new ArrayList<>();
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                int caseValue = this.map[y][x];
                if (caseValue != 0)
                    switch (caseValue) {
                        case 0:
                            // there is nothing on this case
                            // so nothing to do
                            break;
                        case 1:
                            break;
                        case 2:
                            // found the start of a branch
                            PathBranch currentPathBranch = getPathBranch(pathBranches, x, y);
                            treatCurrentPathBranch(context, pathBranches, currentPathBranch);
                            break;
                        case 3:
                            //this is an intersection between two paths we just have to ignore that because we treat this case during the path saving
                            //there is nothing to do
                            break;
                        case 4:
                            break;
                    }
            }
        }

        this.setPathBranches(pathBranches);
    }


    /**
     * Permet de retrouver la position ou commence un chemin à partir des coordonnées d'un des points du chemin et de la direction d'un autre chemin qui croise le chemin que l'on cherche
     *
     * @param x
     * @param y
     * @param direction
     * @return
     */
    private Position getPathBranchStart(int x, int y, Direction direction) {
        int pathLength = 1;
        boolean endPathNotFound = true;

        switch (direction) {
            case UP:
                while (endPathNotFound) {
                    if (this.map[y - pathLength][x] != 2 && this.map[y + pathLength][x] != 2) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                y = y - pathLength;
                break;
            case RIGHT:
                while (endPathNotFound) {
                    if (this.map[y][x + pathLength] != 2 && this.map[y][x - pathLength] != 2) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                x = x + pathLength;
                break;
        }

        return new Position(this.context, x, y);
    }

    /**
     * Permet de récuperer un chemin à partir des coordonées du début du chemin
     *
     * @param pathBranches
     * @param x
     * @param y
     * @return
     */
    private PathBranch getPathBranch(ArrayList<PathBranch> pathBranches, int x, int y) {
        Direction direction = checkDirection(x, y);
        int pathLength = checkPathLength(x, y, direction);

        int midMapSize = (Constants.MAP_SIZE / 2) - 1;
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        int computeX = x - midMapSize + sharedPreferences.getInt("centerX", 0);
        int computeY = y - midMapSize + sharedPreferences.getInt("centerY", 0);

        PathBranch currentPathBranch = new PathBranch(this.context, pathLength, new Position(this.context, computeX, computeY), direction);
        pathBranches.add(currentPathBranch);
        return currentPathBranch;
    }

    /**
     * Permet de traiter l'éventuel présence du début de la map ou effacer les traces d'un chemin déjà enregistré
     *
     * @param context
     * @param pathBranches
     * @param currentPathBranch
     */
    private void treatCurrentPathBranch(Context context, ArrayList<PathBranch> pathBranches, PathBranch currentPathBranch) {

        int midMapSize = (Constants.MAP_SIZE / 2) - 1;
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        int i = currentPathBranch.getStartPosition().getX() + midMapSize - sharedPreferences.getInt("centerX", 0);
        int k = currentPathBranch.getStartPosition().getY() + midMapSize - sharedPreferences.getInt("centerY", 0);
        Direction direction = currentPathBranch.getDirection();
        int pathLength = currentPathBranch.getLenght();

        eraseTreatedCase(i, k);

        for (int j = 1; j <= pathLength; j++) {
            int nextCaseValue = 0;
            int x = 0;
            int y = 0;
            switch (direction) {
                case DOWN:
                    x = i;
                    y = k + j;
                    break;
                case UP:
                    x = i;
                    y = k - j;
                    break;
                case RIGHT:
                    x = i + j;
                    y = k;
                    break;
                case LEFT:
                    x = i - j;
                    y = k;
                    break;
                default:
                    throw new IllegalStateException("Invalid direction");
            }
            nextCaseValue = this.map[y][x];

            if (containAnIntersection(nextCaseValue)) {

                //Après traitement se n'est plus une intersection
                this.map[y][x] = nextCaseValue - 1;

                int intersectionX = x - midMapSize + sharedPreferences.getInt("centerX", 0);
                int intersectionY = y - midMapSize + sharedPreferences.getInt("centerY", 0);

                Intersection currentIntersection = new Intersection(new Position(context, intersectionX, intersectionY));
                currentIntersection.addLinkedPath(currentPathBranch);
                PathBranch secondPathBranch = getSecondPathBranchFromIntersection(pathBranches, currentPathBranch, x, y);
                currentIntersection.addLinkedPath(secondPathBranch);
                currentPathBranch.addIntersection(currentIntersection);
                secondPathBranch.addIntersection(currentIntersection);
            } else {
                if (isTheStartPathBranch(nextCaseValue)) {
                    this.setMapPosition(new Position(context, x, y));
                    this.setStartPathBranch(currentPathBranch);
                }
                eraseTreatedCase(x, y);
            }
        }
    }

    /**
     * Permet de retrouver toutes les caractéristiques d'un chemin à partir des coordonnées d'une intersection entre ce chemin et un chemin passé en parametre
     *
     * @param pathBranches
     * @param currentPathBranch
     * @param x
     * @param y
     * @return
     */
    private PathBranch getSecondPathBranchFromIntersection(ArrayList<PathBranch> pathBranches, PathBranch currentPathBranch, int x, int y) {
        Direction opposedDirection = getOpposedDirection(currentPathBranch);
        Position secondPathBranchPosition = getPathBranchStart(x, y, opposedDirection);
        return getPathBranch(pathBranches, secondPathBranchPosition.getX(), secondPathBranchPosition.getY());
    }

    /**
     * Permet de savoir la direction opposé au chemin actuel
     *
     * @param currentPathBranch
     * @return
     */
    private Direction getOpposedDirection(PathBranch currentPathBranch) {
        Direction opposedDirection;
        if (currentPathBranch.getDirection() == RIGHT || currentPathBranch.getDirection() == LEFT) {
            opposedDirection = UP;
        } else {
            opposedDirection = RIGHT;
        }
        return opposedDirection;
    }

    /**
     * Permet de réinitialiser une case qui a été traité
     *
     * @param x
     * @param y
     */
    private void eraseTreatedCase(int x, int y) {
        this.map[y][x] = 0;
    }

    /**
     * Renvoi true si la case traitée est le début de la map
     *
     * @param nextCaseValue
     * @return
     */
    private boolean isTheStartPathBranch(int nextCaseValue) {
        return nextCaseValue == 8;
    }

    /**
     * Renvoi true si la case est une intersection
     *
     * @param nextCaseValue
     * @return
     */
    private boolean containAnIntersection(int nextCaseValue) {
        return nextCaseValue == 3 || nextCaseValue == 4;
    }

    /**
     * Permet de calculer la longueur d'un chemin
     *
     * @param x
     * @param y
     * @param direction
     * @return
     */
    private int checkPathLength(int x, int y, Direction direction) {
        int pathLength = 1;
        boolean endPathNotFound = true;

        switch (direction) {
            case RIGHT:
                while (endPathNotFound) {
                    if (this.map[y][x + pathLength] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
            case LEFT:
                while (endPathNotFound) {
                    if (this.map[y][x - pathLength] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
            case DOWN:
                while (endPathNotFound) {
                    if (this.map[y + pathLength][x] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
            case UP:
                while (endPathNotFound) {
                    if (this.map[y - pathLength][x] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
        }
        return pathLength;
    }

    /**
     * Permet de récuperer la direction d'un chemin
     *
     * @param x
     * @param y
     * @return
     */
    private Direction checkDirection(int x, int y) {
        Direction direction;

        if (this.map[y][x + 1] != 0) {
            direction = RIGHT;
        } else {
            if (this.map[y][x - 1] != 0) {
                direction = LEFT;
            } else {
                if (this.map[y + 1][x] != 0) {
                    direction = DOWN;
                } else {
                    if (this.map[y - 1][x] != 0) {
                        direction = UP;
                    } else {
                        throw new IllegalStateException("Invalid direction");
                    }
                }
            }
        }
        return direction;
    }

    /**
     * Enregistre une ligne du csv
     *
     * @param ligne
     * @param lineNumber
     */
    public void addLineToTheMatrice(String ligne, int lineNumber) {
        String line[] = ligne.split(";");
        for (int columnNumber = 0; columnNumber < MAP_SIZE; columnNumber++) {
            if (columnNumber < line.length) {
                this.map[lineNumber][columnNumber] = Integer.valueOf(line[columnNumber]);
            } else {
                this.map[lineNumber][columnNumber] = 0;
            }
        }

    }

    /**
     * tranforme le csv chargé en une matrice d'entier
     */
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
        }
    }

}
