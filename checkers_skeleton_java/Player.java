import java.util.*;
import java.lang.*;

public class Player {
  public static GameState nextBestState;
  public static int me;
  public static int you;
  public static int depth;
  public static List checked_states;


  public static int evaluate(GameState state){
    int mine = 0;
    int your = 0;

    for(int pos = 0; pos <= 31; pos++){
        if(state.get(pos) == me){
          mine++;
        }
        else if(state.get(pos) == you){
          your++;
        }
        else if(state.get(pos) == 4+me){
          mine = mine + 10;
        }
        else if(state.get(pos) == 4+you){
          your = your+10;
        }
      }

        return mine-your;
  }

  public static int alphabeta(GameState state,int depth,int alpha,int beta,int player, Deadline deadline){
    int v;
    int bestPossible;
    GameState maxState = new GameState();

    if(deadline.timeUntil() < 100000){
      System.err.println("dedaline!!!!!!");
      return 0;
    }

    Vector<GameState> possibleStates = new Vector<GameState>();
    state.findPossibleMoves(possibleStates); //varför ej beroende av player????????????

    if(depth == 0 || possibleStates.size() == 0) {
      bestPossible = evaluate(state);
    }
    else if(player == me) {
      bestPossible = -Integer.MAX_VALUE;
      for(GameState s: possibleStates){
        if(checked_states.contains(s) == false){
        checked_states.add(s);
        v = alphabeta(s,depth-1,alpha,beta,you, deadline);
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
    }
    else {
      bestPossible = Integer.MAX_VALUE;
      for(GameState s: possibleStates){
        if(checked_states.contains(s) == false){
        checked_states.add(s);
        checked_states.add(s);
        v = alphabeta(s, depth-1, alpha,beta,me,deadline);
        beta = java.lang.Math.min(beta, v);
        if (v < bestPossible) {
         bestPossible = v;
         }
        if(beta <= alpha){
          break;
        }
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
   checked_states = new ArrayList<GameState>();
   depth = 7;
   me = gameState.getNextPlayer();
   you = 3 -me;


     Vector<GameState> nextStates = new Vector<GameState>();
     gameState.findPossibleMoves(nextStates);
     int len = nextStates.size();




     if (nextStates.size() == 0) {
         // Must play "pass" move if there are no other moves possible.
         return new GameState(gameState, new Move());
     }

     int v = alphabeta(gameState, depth,-Integer.MAX_VALUE, Integer.MAX_VALUE, me, deadline);

     /**
      * Here you should write your algorithms to get the best next move, i.e.
      * the best next state. This skeleton returns a random move instead.
      */
     //Random random = new Random();
     //return nextStates.elementAt(random.nextInt(nextStates.size()));
     return nextBestState;
 }
}
