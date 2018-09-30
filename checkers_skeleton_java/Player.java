import java.util.*;
import java.lang.*;

public class Player {

  public static GameState nextBestState;
  public static List checked_states;

  public static int evaluate(GameState state, int player){
    int mine = 0;
    int your = 0;

    for(int pos = 0; pos <= 31; pos++){
        if(state.get(pos) == player){
          mine++;
        }
        else if(state.get(pos) == 0){
          break;
        }
        else{
          your++;
          }
      }

        return mine-your;
  }

  public static int alphabeta(GameState state,int depth,int alpha,int beta,int player, Deadline deadline){
    int v;
    int bestPossible;
    GameState maxState = new GameState();

    if(deadline.timeUntil() < 100000000){
      System.err.println("dedaline!!!!!!");
      return 0;
    }

    Vector<GameState> possibleStates = new Vector<GameState>();
    state.findPossibleMoves(possibleStates); //varför ej beroende av player????????????

    if(depth == 0 || possibleStates.size() == 0) {
      bestPossible = evaluate(state, player);
    }
    else if(player == 1) {
      bestPossible = -Integer.MAX_VALUE;
      for(GameState s: possibleStates){
        if(checked_states.contains(s) == false){
          checked_states.add(s);
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
  }
    else {
      bestPossible = Integer.MAX_VALUE;
      for(GameState s: possibleStates){
        if(checked_states.contains(s) == false){
          checked_states.add(s);
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
    }
    nextBestState = maxState;
    return bestPossible;
  }

    /**
     * Performs a move
     *
     * @param pState
     *            the current state of the board
     * @param pDue
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
    public GameState play(final GameState pState, final Deadline pDue) {
      int depth = 15;
      checked_states = new ArrayList<GameState>();

        Vector<GameState> lNextStates = new Vector<GameState>();
        pState.findPossibleMoves(lNextStates);

        if (lNextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(pState, new Move());
        }

        int v = alphabeta(pState, depth,-Integer.MAX_VALUE, Integer.MAX_VALUE, 1, pDue);


        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
        //Random random = new Random();
        //return lNextStates.elementAt(random.nextInt(lNextStates.size()));

        return nextBestState;

    }
}
