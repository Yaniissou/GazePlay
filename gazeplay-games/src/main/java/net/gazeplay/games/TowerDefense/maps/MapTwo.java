package net.gazeplay.games.TowerDefense.maps;

public class MapTwo extends Map{
    @Override
    public void setStructure() {
        this.map = new int[][] {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,0,0,},
            {0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,},
            {0,0,1,0,0,2,0,0,0,0,2,0,0,0,0,0,0,1,0,0,},
            {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,},
            {0,0,0,2,0,0,0,2,0,0,0,0,0,2,1,0,2,1,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,2,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,2,1,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,2,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
        };
    }
}
