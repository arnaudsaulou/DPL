package com.darkpixellabyrinth.dpl.Levels;

import android.content.Context;

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
import static com.darkpixellabyrinth.dpl.Direction.DOWN;
import static com.darkpixellabyrinth.dpl.Direction.LEFT;
import static com.darkpixellabyrinth.dpl.Direction.RIGHT;
import static com.darkpixellabyrinth.dpl.Direction.UP;

public class MapReader extends Level {

    private int[][] map;
    private int branchCount;
    private Context context;

    public MapReader(Context context) {

        this.context = context;
        this.chargerCsv();
        ArrayList<PathBranch> pathBranches = new ArrayList<>();
        ArrayList<Intersection> intersections = new ArrayList<>();
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int k = 0; k < MAP_SIZE; k++) {
                int caseValue = this.map[i][k];
                if (caseValue != 0)
                    System.out.println("x = " + i + " y = " + k + " value = " + caseValue);
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
                        PathBranch currentPathBranch = getPathBranch(pathBranches, i, k);
                        treatCurrentPathBranch(context, intersections,pathBranches, currentPathBranch);

                        System.out.println(" PathBranch ajouté :  " + currentPathBranch);
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
        this.setIntersections(intersections);
    }


    /**
     * Permet de retrouver la position ou commence un chemin à partir des coordonnées d'un des points du chemin et de la direction d'un autre chemin qui croise le chemin que l'on cherche
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
                    if (this.map[x][y - pathLength] != 2) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                y = y - pathLength;
                break;
            case RIGHT:
                while (endPathNotFound) {
                    if (this.map[x + pathLength][y] != 2) {
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
     * @param pathBranches
     * @param i
     * @param k
     * @return
     */
    private PathBranch getPathBranch(ArrayList<PathBranch> pathBranches, int i, int k) {
        Direction direction = checkDirection(i, k);
        System.out.println("direction :" + direction);
        int pathLength = checkPathLength(i, k, direction);
        System.out.println("length :" + pathLength);
        PathBranch currentPathBranch = new PathBranch(this.context, pathLength, new Position(this.context, i, k), direction);
        pathBranches.add(currentPathBranch);
        return currentPathBranch;
    }

    /**
     * Permet d'enregistrer les intersections présentes sur un chemin, traiter l'éventuel présence du début de la map ou effacer les traces d'un chemin déjà enregistré
     * @param context
     * @param intersections
     * @param pathBranches
     * @param currentPathBranch
     */
    private void treatCurrentPathBranch(Context context, ArrayList<Intersection> intersections,ArrayList<PathBranch> pathBranches, PathBranch currentPathBranch) {
        int i = currentPathBranch.getStartPosition().getX();
        int k = currentPathBranch.getStartPosition().getY();
        Direction direction = currentPathBranch.getDirection();
        int pathLength = currentPathBranch.getLength();

        for (int j = 1; j < pathLength; j++) {
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
            }
            nextCaseValue = this.map[x][y];
            if (containAnIntersection(nextCaseValue)) {
                Intersection currentIntersection = new Intersection(new Position(context, x, y));
                currentIntersection.addLinkedPath(currentPathBranch);
                PathBranch secondPathBranch = getSecondPathBranchFromIntersection(pathBranches, currentPathBranch, x, y);
                currentIntersection.addLinkedPath(secondPathBranch);
                intersections.add(currentIntersection);

                //ya encore un invalid intersection ici alors que l'intersection a bien les 2 chemins liés + tout les attributs fournis je comprends pas :p good luck
                currentPathBranch.addIntersection(currentIntersection);
            } else {
                if (isTheStartPathBranch(nextCaseValue)) {
                    this.setStartPathBranch(currentPathBranch);
                } else {
                    eraseTreatedCase(x, y);
                }
            }
        }
    }

    /**
     * Permet de retrouver toutes les caractéristiques d'un chemin à partir des coordonnées d'une intersection entre ce chemin et un chemin passé en parametre
     * @param pathBranches
     * @param currentPathBranch
     * @param x
     * @param y
     * @return
     */
    private PathBranch getSecondPathBranchFromIntersection(ArrayList<PathBranch> pathBranches, PathBranch currentPathBranch, int x, int y) {
        Direction opposedDirection = getOpposedDirection(currentPathBranch);
        Position secondPathBranchPosition = getPathBranchStart(x,y,opposedDirection);
        return getPathBranch(pathBranches,secondPathBranchPosition.getX(),secondPathBranchPosition.getY());
    }

    /**
     * Permet de savoir la direction opposé au chemin actuel
     * @param currentPathBranch
     * @return
     */
    private Direction getOpposedDirection(PathBranch currentPathBranch) {
        Direction opposedDirection;
        if (currentPathBranch.getDirection() == RIGHT || currentPathBranch.getDirection() == LEFT) {
            opposedDirection = UP;
        }else{
            opposedDirection = RIGHT;
        }
        return opposedDirection;
    }

    /**
     * Permet de réinitialiser une case qui a été traité
     * @param x
     * @param y
     */
    private void eraseTreatedCase(int x, int y) {
        this.map[x][y] = 0;
    }

    /**
     * Renvoi true si la case traitée est le début de la map
     * @param nextCaseValue
     * @return
     */
    private boolean isTheStartPathBranch(int nextCaseValue) {
        return nextCaseValue == 8;
    }

    /**
     * Renvoi true si la case est une intersection
     * @param nextCaseValue
     * @return
     */
    private boolean containAnIntersection(int nextCaseValue) {
        return nextCaseValue == 3 || nextCaseValue == 4;
    }

    /**
     * Permet de calculer la longueur d'un chemin
     * @param i
     * @param k
     * @param direction
     * @return
     */
    private int checkPathLength(int i, int k, Direction direction) {
        int pathLength = 1;
        boolean endPathNotFound = true;
        switch (direction) {
            case DOWN:
                while (endPathNotFound) {
                    if (this.map[i][k + pathLength] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
            case UP:
                while (endPathNotFound) {
                    if (this.map[i][k - pathLength] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
            case RIGHT:
                while (endPathNotFound) {
                    if (this.map[i + pathLength][k] != 0) {
                        pathLength++;
                    } else {
                        endPathNotFound = false;
                    }
                }
                break;
            case LEFT:
                while (endPathNotFound) {
                    if (this.map[i - pathLength][k] != 0) {
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
     * @param i
     * @param k
     * @return
     */
    private Direction checkDirection(int i, int k) {
        Direction direction;
        if (this.map[i][k + 1] != 0) {
            direction = DOWN;
        } else {
            if (this.map[i][k - 1] != 0) {
                int l = k - 1;
                direction = UP;
            } else {
                if (this.map[i + 1][k] != 0) {
                    direction = RIGHT;
                } else {
                    direction = LEFT;
                }
            }
        }
        return direction;
    }

    /**
     * Enregistre une ligne du csv
     * @param ligne
     * @param lineNumber
     */
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
            System.out.println(e.toString());
        }
    }

}
