import java.util.*;
import java.lang.*;

public class Player {
    public static GameState nextBestState;


  public static Integer fixEval(Integer score_val, int cell_val, int player){

    if(cell_val == player){
      if(score_val == null){score_val = 1;}
      if(score_val > 0){score_val++;}
      if(score_val < 0){score_val = 0;}
    }
    if(cell_val == 3-player){
      if(score_val == null){score_val = -1;}
      if(score_val < 0){score_val--;}
      if(score_val > 0){score_val = 0;}
    }
    return score_val;
  }

  public static int evaluate(int player, GameState state){
    int size = state.BOARD_SIZE;
    int score_sum = 0;
    Integer[][] score = new Integer[size*size][3];
    Integer[] diag_score = new Integer[size*6];


    for(int row = 0; row < size; row++){
      for(int col = 0; col < size; col++){
        for(int lay = 0; lay < size; lay++){
          int cell = state.at(row,col,lay);
          score[lay*size+row][0] = fixEval(score[lay*size+row][0], cell, player);
          score[lay*size+col][1] = fixEval(score[lay*size+col][1], cell, player);
          score[row*size+lay][2] = fixEval(score[row*size+lay][2], cell, player);
          if(row+col == size){ diag_score[lay] = fixEval(diag_score[lay], cell, player);}
          if(row == col){ diag_score[size+lay] = fixEval(diag_score[size+lay], cell, player);}
          if(row+lay == size){ diag_score[2*size+col] = fixEval(diag_score[2*size+lay], cell, player);}
          if(row == lay){ diag_score[3*size+col] = fixEval(diag_score[3*size+lay], cell, player);}
          if(col+lay == size){ diag_score[4*size+row] =  fixEval(diag_score[4*size+lay], cell, player);}
          if(col==lay){ diag_score[5*size+row] =  fixEval(diag_score[5*size+lay], cell, player);}
        }
      }
    }

    for(int i = 0; i < size*size; i++){
      for(int j = 0; j < 3; j++){
        if(score[i][j] != null){
          score_sum = score_sum+score[i][j];
        }
      }
    }
    for(int i = 0; i < size*6; i++){
      if(diag_score[i] != null){
        score_sum = score_sum+diag_score[i];
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

     public static int alphabeta(GameState state,int depth,int alpha,int beta,int player, Deadline deadline){
       int v;
       int bestPossible;
       GameState maxState = new GameState();

       if(deadline.timeUntil() < 100000000){
         System.err.println("dedaline!!!!!!");
         break;
       }

       Vector<GameState> possibleStates = new Vector<GameState>();
       state.findPossibleMoves(possibleStates); //varför ej beroende av player????????????

       if(depth == 0 || possibleStates.size() == 0) {
         v = evaluate(player, state);
       }
       else if(player == 1) {
         bestPossible = -Integer.MAX_VALUE;
         for(GameState s: possibleStates){
           v = java.lang.Math.max(v, alphabeta(s,depth-1,alpha,beta,2));
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
           v = java.lang.Math.min(v, alphabeta(s, depth-1, alpha,beta,1));
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


    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
    public GameState play(final GameState gameState, final Deadline deadline) {
      int depth = 3;
        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

        int v = alphabeta(gameState, depth, 1);

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
        //Random random = new Random();
        //return nextStates.elementAt(random.nextInt(nextStates.size()));
        return nextBestState;
    }
}
