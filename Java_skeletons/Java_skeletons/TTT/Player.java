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

     public static Integer fixScore(Integer score,int cell,int player){
       if(cell == player){
         if(score == null){score = 1;}
         if(score > 0){
           score = score*10;
         }
         if(score < 0){score = 0;}
     }
     if(cell == 3-player){
       if(score == null){score = -1;}
       if(score < 0){
         score = score*10;
       }
       if(score > 0){score = 0;}
     }

     return score;
     }


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

           if(cell != 0){
           score[row] = fixScore(score[row], cell, player);
           score[size+col] = fixScore(score[size+col],cell, player);

           if(row == col){
             score[2*size] = fixScore(score[2*size], cell, player);
           }

           if(row + col == size){
             score[2*size+1]=fixScore(score[2*size+1],cell, player);
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

     public static int minmax(GameState state, int depth, int player, Deadline deadline){
       int bestPossible;
       GameState maxState = new GameState();

       if(deadline.timeUntil() < 100000){
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
     }

     public static int alphabeta(GameState state,int depth,int alpha,int beta,int player, Deadline deadline){
            int v;
            int bestPossible;
            GameState maxState = new GameState();

            if(deadline.timeUntil() < 10000000){
              System.err.println("dedaline!!!!!!");
              return 0;
            }

            Vector<GameState> possibleStates = new Vector<GameState>();
            state.findPossibleMoves(possibleStates); //varför ej beroende av player????????????

            if(depth == 0 || possibleStates.size() == 0) {
              bestPossible = evaluate(player, state);
            }
            else if(player == 1) {
              bestPossible = -Integer.MAX_VALUE;
              for(GameState s: possibleStates){
                v = alphabeta(s,depth-1,alpha,beta,2, deadline);
                alpha = java.lang.Math.max(alpha, v);
                if(v > bestPossible){
                  bestPossible = v;
                  maxState  = s;
                }
                if(beta <= alpha){
                  break;
                }
              }
            }
            else {
              bestPossible = Integer.MAX_VALUE;
              for(GameState s: possibleStates){
                v = alphabeta(s, depth-1, alpha,beta,1,deadline);
                beta = java.lang.Math.min(beta, v);
                if (v < bestPossible) {
                 bestPossible = v;
                 maxState  = s;
                 }
                if(beta <= alpha){
                  break;
                }
              }
            }
            nextBestState = maxState;
            return bestPossible;
          }


    public GameState play(final GameState gameState, final Deadline deadline) {
      int depth = 4;
      int alpha = -Integer.MAX_VALUE;
      int beta = Integer.MAX_VALUE;

        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

        int v = alphabeta(gameState, depth, alpha, beta, 1, deadline);

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
        //Random random = new Random();
        //return nextStates.elementAt(random.nextInt(nextStates.size()));
        return nextBestState;
    }

}
