import java.util.*;
import java.lang.*;

public class Player {
  public static int me;
  public static int you;

    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */

     public static Integer fixScore(Integer score, int cell_value){
       if(cell_value == me){
         if(score == null){score = 1;}
         if(score > 0){score = score *10;}
         if(score < 0){score = 0;}
       }
       else if(cell_value == you){
         if(score == null){score = -1;}
         if(score < 0){score = score*10;}
         if(score > 0){score = 0;}
       }
       return score;
     }


     public static int evaluate(GameState state, int depth){
       int v = 0;
       int size = state.BOARD_SIZE;
       Integer[] scores = new Integer[2*size+2];

       if(state.isEOG()){
         if(state.isOWin() && me == 2){v = 99999999;}
         else if(state.isOWin()){v = -99999999;}
         if(state.isXWin() && me == 1){v = 99999999;}
         else if(state.isXWin()){v = -99999999;}
         else{ v = -9999;}
       }
       else{
         for(int row = 0; row < size; row++){
           for(int col = 0; col < size; col++){
             int cell = state.at(row,col);

              scores[row] = fixScore(scores[row], cell);
              scores[size+col] = fixScore(scores[size+col], cell);

              if(row == col){
                scores[2*size] = fixScore(scores[2*size], cell);
              }
              if(row+col == size){
                scores[2*size+1] = fixScore(scores[2*size+1],cell);
              }
           }
         }
         for(Integer value: scores){
           if(value != null){
           v = v + value;
         }
         }
       }

       return v*depth;
     }

    /*public static int minmax(GameState state, int depth, int player, Deadline deadline){
       int bestPossible;
       GameState maxState = new GameState();

       if(deadline.timeUntil() < 10000000){
         return 0;
       }

       Vector<GameState> possibleStates = new Vector<GameState>();
       state.findPossibleMoves(possibleStates);

       if(possibleStates.size() == 0 || depth == 0){
         return evaluate(player, state);
       }

       if(player == 1){
         bestPossible = -Integer.MAX_VALUE;
         for(GameState s: possibleStates){
           int v = minmax(s,depth-1, 2, deadline);
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
           int v = minmax(s,depth-1, 1, deadline);
           //bestPossible = java.lang.Math.min(bestPossible, v);
           if (v<bestPossible) {
            bestPossible = v;
            maxState  = s;
          }
         }
         nextBestState = maxState;
         return bestPossible;
       }
     }*/

     public static int alphabeta(GameState state,int depth,int alpha,int beta,int player, Deadline deadline){
       int v;
       int w;

       Vector<GameState> possibleStates = new Vector<GameState>();
       state.findPossibleMoves(possibleStates);

       if(deadline.timeUntil() < 1000000){
         return 0;
       }

       if(depth == 0 || possibleStates.size() == 0){
         v = evaluate(state, depth);
       }

       else if(player == me){
         v = -Integer.MAX_VALUE;
         for(GameState s: possibleStates){
           w = alphabeta(s,depth-1, alpha, beta, you, deadline);
           v = java.lang.Math.max(v, w);
           alpha = java.lang.Math.max(alpha, v);
           if(beta <= alpha){
             break;
           }
         }
       }
       else{
         v = Integer.MAX_VALUE;
         for(GameState s: possibleStates){
           w = alphabeta(s, depth-1, alpha, beta, me, deadline);
           v = java.lang.Math.min(v, w);
           beta = java.lang.Math.min(beta, v);
           if(beta <= alpha){
             break;
           }
         }
       }
       return v;
     }

    public GameState play(final GameState gameState, final Deadline deadline) {
      int depth = 6;
      int alpha = -Integer.MAX_VALUE;
      int beta = Integer.MAX_VALUE;
      int v;
      int best_v = -Integer.MAX_VALUE;
      me = gameState.getNextPlayer();
      you = 3 -me;


        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);
        GameState nextBestState = nextStates.elementAt(0);

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

        for(GameState s: nextStates){
          v = alphabeta(s, depth, alpha, beta, you, deadline);
          if(v > best_v){
            best_v = v;
            nextBestState = s;
          }
        }

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
        //Random random = new Random();
        //return nextStates.elementAt(random.nextInt(nextStates.size()));
        return nextBestState;
    }

}
