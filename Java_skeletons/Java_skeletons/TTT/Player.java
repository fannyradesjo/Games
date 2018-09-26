import java.util.*;
import java.lang.*;

public class Player {
  public static GameState nextBestState;

    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */

     public static int evaluate(int player, GameState state){
       //points for each marker in a row you are alone in
       int size = state.BOARD_SIZE;
       Integer[] score = new Integer[2*size+2];
       int cell;
       int opponent = 3-player;
       int score_sum = 0;

       for(int row = 0; row < size; row++){
         for(int col = 0; col < size; col++){
           cell = state.at(row,col);
           if(cell == player){
             if(score[row] == null){ score[row] = 1;}
               else{
             if(score[row] > 0){score[row]++;}
             if(score[row] < 0){score[row] = 0;}
           }
           if(score[size+col] == null){ score[size+col] = 1;}
             else{
             if(score[size+col] > 0){score[size+col]++;}
             if(score[size+col] < 0){score[size+col] = 0;}
           }
             if(row == col){
               if(score[2*size] == null){ score[2*size] = 1;}
                 else{
                   if(score[2*size] > 0){score[2*size]++;}
                   if(score[2*size] < 0){score[2*size] = 0;}
                 }
             }
             if(row+col == size-1){
               if(score[2*size+1] == null){ score[2*size+1] = 1;}
                 else{
                   if(score[2*size+1] > 0){score[2*size+1]++;}
                   if(score[2*size+1] < 0){score[2*size+1] = 0;}
                 }
             }
           }

           if(cell == opponent){
             if(score[row] == null){ score[row] = -1;}
               else{
             if(score[row] < 0){score[row]--;}
             if(score[row] > 0){score[row] = 0;}
           }
           if(score[size+col] == null){ score[size+col] = -1;}
             else{
             if(score[size+col] < 0){score[size+col]--;}
             if(score[size+col] > 0){score[size+col] = 0;}
           }
             if(row == col){
               if(score[2*size] == null){ score[2*size] = -1;}
                 else{
                   if(score[2*size] < 0){score[2*size]--;}
                   if(score[2*size] > 0){score[2*size] = 0;}
                 }
             }
             if(row+col == size-1){
               if(score[2*size+1] == null){ score[2*size+1] = -1;}
                 else{
                   if(score[2*size+1] < 0){score[2*size+1]--;}
                   if(score[2*size+1] > 0){score[2*size+1] = 0;}
                 }
             }
             }
           }
         }


       for(int i = 0; i < 2*size+2; i++){
         if(score[i] != null){
           score_sum++;
         }
       }

       return score_sum;
     }

     public static int minmax(GameState state, int depth, int player){
       int bestPossible;
       GameState maxState = new GameState();

       Vector<GameState> possibleStates = new Vector<GameState>();
       state.findPossibleMoves(possibleStates);

       if(possibleStates.size() == 0 || depth == 0){
         return evaluate(player, state);
       }

       if(player == 1){
         bestPossible = -Integer.MAX_VALUE;
         for(GameState s: possibleStates){
           int v = minmax(s,depth-1, 2);
           //bestPossible = java.lang.Math.max(bestPossible, v);
           if (v>=bestPossible) {
            bestPossible = v;
            maxState  = s;
          }
         }
         nextBestState = maxState;
         return bestPossible;
       }

       else{
         bestPossible = Integer.MAX_VALUE;
         for(GameState s: possibleStates){
           int v = minmax(s,depth-1, 1);
           //bestPossible = java.lang.Math.min(bestPossible, v);
           if (v<bestPossible) {
            bestPossible = v;
            maxState  = s;
          }
         }
         nextBestState = maxState;
         return bestPossible;
       }
     }

     public static int alphabeta(GameState state,int depth,int alpha,int beta,int player){
       int v;

       Vector<GameState> possibleStates = new Vector<GameState>();
       state.findPossibleMoves(possibleStates); //varför ej beroende av player????????????

       if(depth == 0 || possibleStates.size() == 0) {
         v = evaluate(player, state);
       }
       else if(player == 1) {
         v = -Integer.MAX_VALUE;
         for(GameState s: possibleStates){
           v = java.lang.Math.max(v, alphabeta(s,depth-1,alpha,beta,2));
           alpha = java.lang.Math.max(alpha, v);
           if(beta <= alpha){
             break;
           }
         }
       }
       else {
         v = Integer.MAX_VALUE;
         for(GameState s: possibleStates){
           v = java.lang.Math.min(v, alphabeta(s, depth-1, alpha,beta,1));
           beta = java.lang.Math.min(beta, v);
           if(beta <= alpha){
             break;
           }
         }
       }
       return v;
     }

    public GameState play(final GameState gameState, final Deadline deadline) {
        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

        int v = minmax(gameState, 3, 1);

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
        //Random random = new Random();
        //return nextStates.elementAt(random.nextInt(nextStates.size()));
        return nextBestState;
    }
}
