import java.util.ArrayList;
import java.util.Random;

public class SimplifiedOkeyGame {

    Player[] players;
    Tile[] tiles;
    int tileCount;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public SimplifiedOkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // four copies of each value, no jokers
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i);
            }
        }

        tileCount = 104;
    }

    /*
     * DONE *
     * distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        int k = 15;
        for(int i = 0; i<players.length; i++){
            if(i==0){
                for(int m = 0; m<15; m++){
                    players[i].addTile(tiles[m]);
                }
            }
            else{
                for(int n = k; n<k+14; n++){
                    players[i].addTile(tiles[n]);
                }
            }
            k += 14; 
            
        }

        
    }

    /* DONE
     * get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        players[ currentPlayerIndex ].addTile(lastDiscardedTile);
        Tile picked = lastDiscardedTile;
        lastDiscardedTile = null;
        return picked.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * and it will be given to the current player
     * returns the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        return null;
    }

    /*
     * DONE *
     * should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        Random rand = new Random(); // instance of random class
        int n = tiles.length;
        for (int i = n - 1; i > 0; i--) {
            // Generate a random number between 0 and i (inclusive)
            int j = rand.nextInt(i+1);
            // Swap tiles[i] with the element at random index
            Tile temp = tiles[i];
            tiles[i] = tiles[j];
            tiles[j] = temp;            
        }
        /* if needed for checking tiles
        for (int i : array) {
            System.out.print(i + " ");
        } */

    }

    /*
     * DONE * 
     * check if game still continues, should return true if current player
     * finished the game. use checkWinning method of the player class to determine
     */
    public boolean didGameFinish() {
        if(players[currentPlayerIndex].checkWinning()){
            return true;
        }
        return false;
    }

    /* TODO: finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     */
    public Player[] getPlayerWithHighestLongestChain() {
        Player[] winners = new Player[1];

        return winners;
    }
    
    /*
     * checks if there are more tiles on the stack to continue the game
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
    }

    /*  DONE BUT NEEDS TESTING
     * pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * you should check if getting the discarded tile is useful for the computer
     * by checking if it increases the longest chain length, if not get the top tile
     */
    public void pickTileForComputer() {
        Tile[] tiles = players[ currentPlayerIndex ].getTiles();
        for( int i = 0; i <tiles.length; i++ ){
            if( tiles[ i ].matchingTiles(lastDiscardedTile) ){ 
                // if lastDiscardedTile is matching with player's tile, then get top tile 
                getTopTile();
                return;
            }
        }

        int startIndex = 0, endIndex = 0;
        ArrayList<Integer> starts,ends;
        starts = new ArrayList<>();
        ends = new ArrayList<>();
        starts.add(0);
        ends.add(0);
        for( int i = 1; i < tiles.length ; i++ ){
            if( tiles[ i - 1 ].canFormChainWith(tiles[ i ]) || tiles[ i - 1 ].matchingTiles(tiles[ i ])){
                endIndex = i;
            }
            else{
                starts.add(startIndex);
                ends.add(endIndex);
                startIndex = i;
                endIndex = i;
            }
        }
        starts.add(startIndex);
        ends.add(endIndex);
        
        ends.add(27);
        starts.add(27);
        int valueOfLastDiscarded = lastDiscardedTile.getValue();
        int costsSum = 0, costsCount = 0;
        
        int currentCost = 0, currentCostWithTile = 0, minCost = 27 ;
        for(int i = 1; i < starts.size() - 1; i++){
            currentCost = 0;
            currentCostWithTile = 0;
            for(int j = i; j < starts.size() - 1; j++){
                if( valueOfLastDiscarded < starts.get( i ) && valueOfLastDiscarded > ends.get( i - 1 )){

                }
                else if( valueOfLastDiscarded < starts.get( j + 1 ) && valueOfLastDiscarded > ends.get( j )){

                }
                if( i != j ){
                    if( valueOfLastDiscarded < starts.get( j ) && valueOfLastDiscarded > ends.get( j  - 1))
                        currentCostWithTile += starts.get( j ) - ends.get( j - 1 ) - 1;
                    else
                        currentCostWithTile += starts.get( j ) - ends.get( j - 1 ); 
                    currentCost += starts.get( j ) - ends.get( j - 1 );
                }
                if( ends.get( j ) - starts.get( i ) + 1 >= 14){
                    for( int k = i; k <= j; k++){
                        costsSum += currentCost;
                        costsCount ++;
                        if (currentCost != currentCostWithTile)
                            minCost = Math.min(minCost, currentCostWithTile);
                    }
                    break;
                }
                else if( ends.get( j ) - starts.get( i ) + 1 + ( starts.get( i ) - ends.get( i - 1 ) - 1 ) + ( starts.get( j + 1 ) - ends.get( j ) - 1 ) >= 14 ){
                    int countOfNeededTiles = 14 - ends.get( j ) - starts.get( i ) + 1;
                    if( valueOfLastDiscarded < starts.get( j + 1 ) && valueOfLastDiscarded > ends.get( j )){
                        if( ends.get( j ) + countOfNeededTiles >= valueOfLastDiscarded)
                            minCost = Math.min(minCost, currentCost + (14 - ( ends.get( j ) - starts.get( i ) + 1 )) - 1);
                    }
                    
                    costsSum += currentCost + (14 - ( ends.get( j ) - starts.get( i ) + 1 ));
                    costsCount ++;
                    continue;
                }
            }
        }
        double averageOfCosts = ((double)costsSum)/((double)costsCount);
        if( minCost <= (int)(averageOfCosts + 1)){
            System.out.println(getLastDiscardedTile());
        }
        else{
            System.out.println(getTopTile()); 
        }
        
    }

    /* DONE
     * Current computer player will discard the least useful tile.
     * you may choose based on how useful each tile is
     */
    public void discardTileForComputer() {
        Tile[] tiles = players[ currentPlayerIndex ].getTiles();
        for( int i = 1; i < tiles.length ; i++ ){
            if( tiles[i].matchingTiles( tiles[ i - 1 ]) ){
                discardTile( i );// discardingthe duplicate
                return;
            }
        }
        int startIndex = 0, endIndex = 0;
        ArrayList<Integer> starts,ends;
        starts = new ArrayList<>();
        ends = new ArrayList<>();
        starts.add(0);
        ends.add(0);
        for( int i = 1; i < tiles.length ; i++ ){
            if( tiles[ i - 1 ].canFormChainWith(tiles[ i ])){
                endIndex = i;
            }
            else{
                starts.add(startIndex);
                ends.add(endIndex);
                startIndex = i;
                endIndex = i;
            }
        }
        starts.add(startIndex);
        ends.add(endIndex);
        
        ends.add(27);
        starts.add(27);
        int[] costs = new int[ ends.size() - 1 ];
        for( int i = 0; i < costs.length; i++){
            costs[ i ] = 27;
        } 
        int currentCost = 0 ;
        for(int i = 1; i < starts.size() - 1; i++){
            currentCost = 0;
            for(int j = i; j < starts.size() - 1; j++){
                if( i != j ){
                    currentCost += starts.get( j ) - ends.get( j - 1 );
                }
                if( ends.get( j ) - starts.get( i ) + 1 >= 14){
                    for( int k = i; k <= j; k++){
                        costs[ k ] = Math.min( costs[ k ], currentCost);
                    }
                    break;
                }
                else if( ends.get( j ) - starts.get( i ) + 1 + ( starts.get( i ) - ends.get( i - 1 ) - 1 ) + ( starts.get( j + 1 ) - ends.get( j ) - 1 ) >= 14 ){
                    for( int k = i; k <= j; k++){
                        costs[ k ] = Math.min( costs[ k ], currentCost + (14 - ( ends.get( j ) - starts.get( i ) + 1 )));
                    }
                    continue;
                }
            }
        }
        int maxIndex = 1, maxValue = 0;
        for(int i = 1; i < costs.length; i++){
            if( costs[ i ] > maxValue){
                maxIndex = i;
                maxValue = costs[ i ];
            }
        }
        for( int i = 0; i < tiles.length; i++){
            if( tiles[ i ].getValue() == starts.get(maxIndex) ){
                startIndex = i;
            }
            else if( tiles[ i ].getValue() == ends.get(maxIndex) ){
                endIndex = i;
                break;
            }
        }
        if( (tiles[ startIndex ].getValue() - 1) < ( 14 - tiles[ endIndex ].getValue() ) ){
            discardTile( startIndex );
        }
        else{
            discardTile( endIndex );
        }

    }

    /*
     * DONE
     * discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[ currentPlayerIndex ].getAndRemoveTile(tileIndex);

    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
