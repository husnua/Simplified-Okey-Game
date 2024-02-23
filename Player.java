import java.util.Arrays;

public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * DONE * 
     * checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     */
    public boolean checkWinning() {
        int counter = findLongestChain();
        if(counter>=14){
            return true;
        }
        return false;
    }

    /*
     * DONE
     * used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */
    public int findLongestChain() {
        int longestChain = 0;
        int currentChain = 1;
        for ( int i = 1; i < playerTiles.length; i++){
            if( playerTiles[ i ].canFormChainWith(playerTiles[ i - 1 ]) ){
                currentChain += 1;
            }
            else if( playerTiles[ i ].matchingTiles( playerTiles[ i - 1 ])){
                continue;
            }
            else{
                if( longestChain < currentChain ){
                    longestChain = currentChain;
                } 
                currentChain = 1;
            }
        }
        if( longestChain < currentChain ){
            longestChain = currentChain;
        } 
        return longestChain;
    }

    /*
     * DONE: removes and returns the tile in given index position
     */
    public Tile getAndRemoveTile(int index) {
        Tile wantedTile = this.playerTiles[index];
        Tile [] tempPlayerTiles = new Tile[playerTiles.length-1];
        for (int i = 0, j = 0; i < playerTiles.length; i++) {
            if (i != index) {
                tempPlayerTiles[j++] = playerTiles[i];
            }
        }

        return wantedTile;
    }

    /*
     * DONE: adds the given tile to this player's hand keeping the ascending order
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     */
    public void addTile(Tile t) {
        int index = 0;

        // finding the correct position for the tile to be inserted
        while ( index < playerTiles.length && playerTiles[index].getValue() < t.getValue()) { 
            index++;
        }
        // Shifting all the elements in the playerTiles array to the right by one
        for (int i = playerTiles.length - 1; i > index; i--) {
            playerTiles[i] = playerTiles[i - 1];
        }
        // Inserting the new tile to the array
        playerTiles[index] = t;
    }

    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /*
     * displays the tiles of this player
     */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
