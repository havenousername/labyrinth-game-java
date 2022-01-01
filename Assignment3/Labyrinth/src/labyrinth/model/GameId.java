/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author andreicristea
 */
public class GameId implements Comparable<GameId> {
    public final String difficulty;
    public final int level;
    public final LevelSrcs levelSrcs;
    private final String pattern;
    
    
    public GameId(String difficulty, final int level, final String pattern, LevelSrcs levelSrcs) {
        this.difficulty = difficulty;
        this.level = level;
        String newStr = pattern.replace("\n", "");
        final String regex = "^(?=.*\\.)(?=.*\\#)(?=.*P)(?=.*E)(?=.*\\ )[.#PE ]{" + newStr.length() + "}$";
        final Pattern pat = Pattern.compile(regex, Pattern.MULTILINE);
        if (!pat.matcher(newStr).matches()) {
            throw new IllegalArgumentException("Pattern does not match provided regex");
        }
        this.pattern = pattern;
        this.levelSrcs = levelSrcs;
    }
    
    public List<List<String>> getLevel() {
        return Stream.of(pattern.split("\n"))
                .map(elem -> {
                    return Stream.of(elem.split("")).map(e -> new String(e)).collect(Collectors.toList());
                })
                .collect(Collectors.toList());
    }
    
    public String getId() {
        return getId(difficulty, level);
    }
    
    public static String getId(String difficulty, int level) {
        return difficulty + " " + level;
    }
    
    public static int getLevel(String id) {
        return Integer.parseInt(id.split(" ")[1]);
    }
    
    public static String getDifficulty(String id) {
        return id.split(" ")[0];
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.difficulty);
        hash = 97 * hash + this.level;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameId other = (GameId) obj;
        if (this.level != other.level) {
            return false;
        }
        if (!Objects.equals(this.difficulty, other.difficulty)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(GameId o) {
        if (Difficulty.getRepresentation(o.difficulty) == Difficulty.getRepresentation(difficulty)) {
            return level > o.level ? 1 : level < o.level ? -1 : 0;
        } else if (Difficulty.getRepresentation(o.difficulty) > Difficulty.getRepresentation(difficulty)) {
            return -1;
        } else {
            return 1;
        }
    }
}
