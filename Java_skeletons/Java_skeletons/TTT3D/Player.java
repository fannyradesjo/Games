import java.util.*;
import java.lang.*;

public class Player {
    public static GameState nextBestState;
    public static int me;
    public static int you;
    public static int depth;
    public static List checked_states;



  /*  public static Integer fixEval(Integer score_val, int cell_val){

    if(cell_val == me){
      if(score_val == null){score_val = 1;}
      if(score_val > 0){score_val = score_val*100;}
      if(score_val < 0){score_val = 0;}
    }
    if(cell_val == you){
      if(score_val == null){score_val = -1;}
      if(score_val < 0){score_val = score_val*200;}
      if(score_val > 0){score_val = 0;}
    }
    return score_val;
  }

  public static int evaluate(GameState state){
    int size = state.BOARD_SIZE;
    int score_sum = 0;
    Integer[][] score = new Integer[size*size][3];
    Integer[] diag_score = new Integer[size*6+4];
    int v = 0;

    if(state.isEOG()){
      if(state.isOWin() && me == 2){v = 999999999;}
      else if(state.isOWin()){v = -999999999;}
      if(state.isXWin() && me == 1){v = 999999999;}
      else if(state.isXWin()){v = -999999999;}
      else{ v = -99999;}
    }
    else{
    for(int row = 0; row < size; row++){
      for(int col = 0; col < size; col++){
        for(int lay = 0; lay < size; lay++){
          int cell = state.at(row,col,lay);
          score[lay*size+row][0] = fixEval(score[lay*size+row][0], cell);
          score[lay*size+col][1] = fixEval(score[lay*size+col][1], cell);
          score[row*size+lay][2] = fixEval(score[row*size+lay][2], cell);
          if(row+col == size){ diag_score[lay] = fixEval(diag_score[lay], cell);}
          if(row == col){ diag_score[size+lay] = fixEval(diag_score[size+lay], cell);}

          if(row+lay == size){ diag_score[2*size+col] = fixEval(diag_score[2*size+col], cell);}
          if(row == lay){ diag_score[3*size+col] = fixEval(diag_score[3*size+col], cell);}
          if(col+lay == size){ diag_score[4*size+row] =  fixEval(diag_score[4*size+row], cell);}
          if(col==lay){ diag_score[5*size+row] =  fixEval(diag_score[5*size+row], cell);}

          if(row == col && lay == col){ diag_score[6*size] =  fixEval(diag_score[6*size], cell);}
          if(row == col && lay+row == size){ diag_score[6*size+1] =  fixEval(diag_score[6*size+1], cell);}
          if(row+col == size && lay == row){ diag_score[6*size+2] =  fixEval(diag_score[6*size+2], cell);}
          if(row+col == size && lay == col){ diag_score[6*size+3] =  fixEval(diag_score[6*size+3], cell);}
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
    for(int i = 0; i < size*6+4; i++){
      if(diag_score[i] != null){
        score_sum = score_sum+diag_score[i];
      }
    }
    v = score_sum;
  }
    return v;
  }*/

  public static int evaluate(GameState state){
    int[][] heuristics = {{0, -10, -100, -1000, -10000}, {10, 0, 0, 0, 0}, {100, 0, 0, 0, 0}, {1000, 0, 0, 0, 0},{10000, 0, 0, 0, 0}};
    int o = 0;
    int p = 0;
    int t = 0;
    int cell_value;

    for(int i = 0; i<16; ++i)
  {
    p = 0; o = 0;
    for(int j = 0; j<4; ++j)
      {
        cell_value = state.at(i*4+j);
        if(cell_value == me)
        p++;
        else if(cell_value == you)
        o++;
      }
      t += heuristics[p][o];
    }

    for(int i = 0; i<=48; i=i+16)
  	{
  	   for(int k = 0; k<4; ++k)
  		{
        p = 0; o = 0;
  		for(int j = 0; j<4; ++j)
      {
        cell_value = state.at(k+ j*4+i);
        if(cell_value == me)
        p++;
        else if(cell_value == you)
        o++;
      }
      t += heuristics[p][o];
  		}
  	}

    for(int i = 0; i<16; ++i)
    {
      p = 0; o = 0;
      for(int j = 0; j<4; ++j)
        {
          cell_value = state.at(i+16*j);
          if(cell_value == me)
          p++;
          else if(cell_value == you)
          o++;
        }
        t += heuristics[p][o];
      }

      for(int i = 0; i<=48; i = i + 16)
      {
        p = 0; o = 0;
        for(int j = 0; j<=3; ++j)
          {
            cell_value = state.at(i+j*5);
            if(cell_value == me)
            p++;
            else if(cell_value == you)
            o++;
          }
          t += heuristics[p][o];
        }

        for(int i = 3; i<=51; i = i + 16)
        {
          p = 0; o = 0;
          for(int j = 0; j<=3; ++j)
            {
              cell_value = state.at(i+j*3);
              if(cell_value == me)
              p++;
              else if(cell_value == you)
              o++;
            }
            t += heuristics[p][o];
          }

          for(int i = 0; i<=3; i = i + 1)
          {
            p = 0; o = 0;
            for(int j = 0; j<=60; j=j+20)
              {
                cell_value = state.at(i+j);
                if(cell_value == me)
                p++;
                else if(cell_value == you)
                o++;
              }
              t += heuristics[p][o];
            }

            for(int i = 0; i<=3; i = i + 1)
            {
              p = 0; o = 0;
              for(int j = 12; j<=48; j = j + 12)
                {
                  cell_value = state.at(i+j);
                  if(cell_value == me)
                  p++;
                  else if(cell_value == you)
                  o++;
                }
                t += heuristics[p][o];
              }

              for(int i = 0; i<=3; i = i + 1)
              {
                p = 0; o = 0;
                for(int j = 0; j<=51; j=j+17)
                  {
                    cell_value = state.at(i*4+j);
                    if(cell_value == me)
                    p++;
                    else if(cell_value == you)
                    o++;
                  }
                  t += heuristics[p][o];
                }

                for(int j = 3; j<=15; j = j + 4)
                {
                  p = 0; o = 0;
                  for(int i = 0; i<=3; ++i)
                    {
                      cell_value = state.at(i*15+j);
                      if(cell_value == me)
                      p++;
                      else if(cell_value == you)
                      o++;
                    }
                    t += heuristics[p][o];
                  }

                  p = 0; o = 0;
                  for(int i = 0; i<=63; i = i + 21)
                    {
                      cell_value = state.at(i);
                      if(cell_value == me)
                      p++;
                      else if(cell_value == you)
                      o++;
                    }
                    t += heuristics[p][o];

                    p = 0; o = 0;
                    for(int i = 3; i<=60; i = i + 19)
                      {
                        cell_value = state.at(i);
                        if(cell_value == me)
                        p++;
                        else if(cell_value == you)
                        o++;
                      }
                      t += heuristics[p][o];

                      p = 0; o = 0;
                      for(int i = 12; i<=51; i = i + 13)
                        {
                          cell_value = state.at(i);
                          if(cell_value == me)
                          p++;
                          else if(cell_value == you)
                          o++;
                        }
                        t += heuristics[p][o];

                        p = 0; o = 0;
                        for(int i = 15; i<=48; i = i + 11)
                          {
                            cell_value = state.at(i);
                            if(cell_value == me)
                            p++;
                            else if(cell_value == you)
                            o++;
                          }
                          t += heuristics[p][o];



  return t;
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
      depth = 1;
      me = gameState.getNextPlayer();
      you = 3 -me;


        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);
        int len = nextStates.size();
        //if(len < 40){depth = 4;}
        //if(len < 30){depth = 7;}
        //if(len < 15){depth = 7;}*/



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
