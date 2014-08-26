import java.util.Arrays;
import java.util.Random;

public class PathFinder {
	private static int nrows = 6, ncols = 6, final_state = 5, initial_state = 2;
	private static double gama = 0.8;
    private static int[][]  qmatrix, rmatrix;
    
   public static void main(String[] args) {
	   qmatrix = rmatrix = new int[nrows][ncols] ;
	   set_rmatrix(nrows,ncols); // R matrix this will depict the connection between rooms/states.
	   set_qmatrix(nrows,ncols); //matrix whose values will be updated by path_finder class.
	   
	   System.out.println(Arrays.deepToString(rmatrix));
	   //loop for running episodes 100 times
	   for(int i=0; i< 100; i++){
		   Random rand = new Random();
		   int current_state, next_state;
		   current_state = rand.nextInt(5 + 1);
//		   System.out.println(Arrays.deepToString(rmatrix));
		   next_state = next_state(rand, current_state, rmatrix);
		   //this loop depicts one episode
		   do{
			  int max = max(qmatrix, next_state,ncols);
			  qmatrix[current_state][next_state] = rmatrix[current_state][next_state] + (int)(gama*max);
//			  System.out.println("qmatrix=="+qmatrix[current_state][next_state]);  
			  current_state = next_state;
			  next_state = next_state(rand, current_state, rmatrix);
//			  System.out.println("next_state= "+next_state+"  action= "+ rmatrix[current_state][next_state]);
			  // next state will be chosen from current state row
		   }while(next_state != final_state); 
	   }
	   
	   System.out.println(Arrays.deepToString(qmatrix));
	   
	   String path = get_path(initial_state, final_state, qmatrix, ncols );
	   
	   System.out.println("path ===>" + path);
		   
	      
	   
   }
   
   public static String get_path(int initial_state, int final_state, int[][] qmatrix, int ncols ){
	   
	   int current_state = initial_state;
	   String path = String.valueOf(current_state);
	   do{
		   int next_state = next_optimal_state(qmatrix, current_state, ncols);
		   path = (next_state!= -1) ?  (path + "-" + next_state) : path;
		   current_state = next_state;
	   }while(current_state!= final_state);
	   

	   return path;
   }
   
   public static int next_optimal_state(int[][] data, int row_no, int ncols){
	   int max = -1, next_state=-1;   
	   for(int i=0; i < ncols; i++){
//		   data[row_no][i];
		   int current_element = data[row_no][i];
		   if (max < current_element){
			   max = current_element;
			   next_state = i;
		   }
	   }
	   return next_state;
   }
   //will be used to get next random but valid 'next state'/action for current state. 
   public static int next_state(Random rand, int current_state, int[][] rmatrix){
	   int next_state;
	 //do while loop checks for the next state value to be in current_state row as non -ve i.e; there is path between them.
	   do {
		   next_state = rand.nextInt(5 + 1);
//		   System.out.println("next_state= "+next_state+"  action= "+ rmatrix[current_state][next_state]);
		} while (current_state == next_state || rmatrix[current_state][next_state]== -1);
	    
	   return next_state;
   }
   
   // R matrix will be build by this function.
   public static void set_rmatrix(int nrows, int ncols){
	// initializing R matrix
       for(int i=0; i< nrows; i++ ){
  	     for(int j=0; j< ncols; j++ ){
  	    	rmatrix[i][j] = -1;    
  		 }   
  	   }
       rmatrix[0][4] = rmatrix[1][3] = rmatrix[2][3] = rmatrix[3][1] = rmatrix[3][2] = rmatrix[3][4] = rmatrix[4][0] = rmatrix[4][3] = rmatrix[5][1] = rmatrix[5][4] = 0;
       rmatrix[1][5] = rmatrix[4][5] = rmatrix[5][5] = 100;
   }
// create M-by-N matrix of 0's, Q matrix will be build by this function.
   public static void set_qmatrix(int nrows, int ncols) {
	   nrows = qmatrix.length;
	   ncols = qmatrix[0].length;
	   qmatrix= new int[nrows][ncols];
       // initializing Q matrix
       for(int i=0; i< nrows; i++ ){
  	     for(int j=0; j< ncols; j++ ){
  	    	qmatrix[i][j] = 0;    
  		 }
  	   }
   }
   
   // max: will return the maximum value in a row of matrix to be used in learning formula.
   public static int max(int[][] data, int row_no, int ncols){
	   int max = -1;
	   for(int i=0; i < ncols; i++){
		   int current_element = data[row_no][i];
		   if (max < current_element){
			   max = current_element;
		   }
	   }
	   return max;
   }
   
   public int[][] pick_actions(int[][] data, int row_no, int ncols){
	   int[] actions = new int[ncols];
	   int count = 0;
	   for(int i=0; i < ncols; i++){
		   int current_element = data[row_no][i];
		   if(current_element != -1){
			   actions[count++] = current_element; 
		   }
	   }
	   return data;
   }
}
