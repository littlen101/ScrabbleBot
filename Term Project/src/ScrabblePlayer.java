import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/*

  Authors (group members):
  Email addresses of group members:
  Group name: Best Scrabble Bot Ever!

  Course: cse2010
  Section: 14

  Description of the overall algorithm and key data structures:


*/



public class ScrabblePlayer
{
	//The best multiplier that can be applied to the word in a given column or row
	//the 10's number is the word multiplier; The 1's is the letter multiplier 
	public static final byte[] FunctionMultiplier = {92, 23, 22, 24, 26, 4, 52};

	//Standard Trie Dictionary File
	WordGenerator dictionary;
	
	/* TODO //HoffmanEnoding 
	HoffmanEncoding enigma;
	//Flag whether or not encoding is possible
	boolean encode;*/
	
    // initialize ScrabblePlayer with a file of English words
    public ScrabblePlayer(String wordFile) {
    	FileInputStream fis;
    	ObjectInputStream ois = null;
		try {
			//Try to upload previously generated dictionary
			//Creates the file stream object 
			fis = new FileInputStream("generator.ser");
			ois = new ObjectInputStream(fis);
		} catch (IOException e){
			//If the upload fails do fresh construction 
			dictionary = new WordGenerator(wordFile);
		}
		
		if (dictionary == null) {try {
			//If object is successfully created initialize the dictionary with it 
			dictionary = (WordGenerator) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			//If the object failed to be uploaded construct a new one
			dictionary = new WordGenerator(wordFile);
		}}
    	
		//TODO
		/*encode = true;
		try {
			fis = new FileInputStream("hoffmantree.ser");
			ois = new ObjectInputStream(fis);
		} catch (IOException e){
			encode = false;
		}
		
		if (encode) {try {
			enigma = (HoffmanEncoding) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			encode = false;
		}}*/
    }

    // based on the board and available letters, 
    //    return a valid word with its location and orientation
    //    See ScrabbleWord.java for details of the ScrabbleWord class 
    //
    // board: 15x15 board, each element is an UPPERCASE letter or space;
    //    a letter could be an underscore representing a blank (wildcard);
    //    first dimension is row, second dimension is column
    //    ie, board[row][col]     
    //    row 0 is the top row; col 0 is the leftmost column
    // 
    // availableLetters: a char array that has seven letters available 
    //    to form a word
    //    a blank (wildcard) is represented using an underscore '_'
    //

    public byte goDown (byte start, char[][] board) {
    	for(byte i = 0; i < board.length; i++) {
    		if (board[i][start] != ' ') return i;
    	}
		return -1;
    }
    
    public byte goRight (byte row, byte start, char[][] board) {
    	for(byte i = start; i < board.length; i++) {
    		if (board[row][i] != ' ') return  i;
    	}
		return -1; 	
    }
    
    public byte goLeft (byte row, byte start, char[][] board) {
    	for(byte i = start; i >= 0; i--) {
    		if (board[row][i] != ' ') return  i;
    	}
		return -1;
    }
    

    public byte findDirection (byte x, byte y, char[][] board) {
    	if(board[x + 1][y] != ' ') return 2;       //word is going down
    	else if (board[x][y + 1] != ' ') return 1; //word is going right
    	else if (board[x][y - 1] != ' ') return 3; //word is going left

		return 0;
    }
    
    public List<Character> getWords(byte x, byte y,byte direction, char[][] board) {
    	List<Character> word = new ArrayList<Character>();
    	while(board[x][y] != ' ') {
    		word.add(board[x][y]);
    		if (direction == 1) y++;
    		else if (direction == 2) x++;
    		else y--;
    	}
    	
		return word;
    	
    }

    public byte findQudrant (byte x, byte y) {
    	byte quad = 0;

    	if (x <= 7 &&  y <= 7) quad = 1;
    	else if (x <= 7 && y > 7) quad = 2;
    	else if (x > 7 && y <= 7) quad = 3;
    	else if (x > 7 && y > 7) quad = 4;
    	return quad;
    }

    public BoardWord boardSearch (char[][] board) {
		byte colStart = 7;
		byte position = goDown(colStart, board); //y
		byte rowUse = colStart; // x
		byte direction = 0;
		
		if (position < 0) {
			//goes through the rows
			for(byte i = 0; i < board.length; i++) {
				if (position < 0) {
					position = goRight(i, (byte) (colStart + 1), board);
					if(position >= 0) {
						direction = findDirection(i, position, board);
						rowUse = i;
					}
				} else {break;}
				if (position < 0) {
					position = goLeft(i,(byte) (colStart -1), board);
					if(position >= 0) {
						direction = findDirection(i, position, board);
						rowUse = i;
					}
				} else {break;}
			} 
		} else {
			rowUse = position;
			position = colStart;
		}

		List<Character> word = getWords(rowUse, position ,direction, board);
		
		if(direction == 3) {direction = 1; position -= (word.size() - 1); Collections.reverse(word);}
		
		byte encodedLength = (byte) (word.size() * 10 + findQudrant(rowUse, position));
		byte encodedDirection = (byte) ((direction == 1)? 
					((direction * 10) + findQudrant(rowUse, (byte)(position + word.size() - 1))) :
						 ((direction * 10) + findQudrant((byte) (rowUse + word.size() - 1),  position)));

		return new BoardWord(word, rowUse, position, encodedLength, encodedDirection);
    }
    
    public byte generateHand(HashMap<Character, Tile> hand, char[] letters) {
    	byte count = 0;
    	for(char l : letters) {
    		if(hand.containsKey(l)) { hand.get(l).charCount += 1; }
    		else { if (l == '_') { count = ((byte) (count + 1)); continue;}
    			hand.put(l, new Tile(l, Tile.getValue(l)));}
    	}
    	return (byte) count;
    }

    public short determineBonus (short value, byte letterScore, byte index, byte quad, char direction) {
    	short result = 0;
		if(direction == 'v') {
			if(quad < 3) {
				result = (short) ((FunctionMultiplier[index]/10) * 
											(value + ((FunctionMultiplier[index]%10) * letterScore)));
			} else {
				result = (short) ((FunctionMultiplier[FunctionMultiplier.length - index]/10) * 
					(value + ((FunctionMultiplier[FunctionMultiplier.length - index]%10) * letterScore)));
			}
		} else {
			if(quad % 2 != 0) {
				result = (short) ((FunctionMultiplier[index]/10) * 
						(value + ((FunctionMultiplier[index]%10) * letterScore)));
			} else {
				result = (short) ((FunctionMultiplier[FunctionMultiplier.length - index]/10) * 
					(value + ((FunctionMultiplier[FunctionMultiplier.length - index]%10) * letterScore)));
			}
		}
	
		return result;
    }
    
    public short sufficentPoints(ArrayList<Character> list, byte index, byte quad, char direction) {
    	if (quad == 0 || index < 0) return 0;
    	short sum = 0;
    	byte max = -127;

    	for(Character l : list) {
    		byte val = Tile.getValue(l);
    		max = (byte) Math.max(val, max);
    		sum += val;
    	}

    	return determineBonus(sum, max, index, quad, direction);
    }

    public byte wordValue(List<Character> word) {
    	byte sum = 0;
    	for(char c : word) {
    		sum += Tile.getValue(c);
    	}
    	
    	return sum;
    }

    public boolean legal (byte start, byte length) {
    	return (start + (length - 1)) < 15;
    }

    
    public byte playStaticCharacter (char staticCharacter) {
    	dictionary.goToLetter(staticCharacter);
		
    	byte position = dictionary.getLength();
    	position -= 1;
    	return position;
    }

    public void playHandCharacter (PriorityQueue<Tile> queue, PlayerHand player,
    													ArrayList<Tile> rejectedLetters) {
    	boolean moved = false;
    	while ((!queue.isEmpty()) && !moved) {
	    	Tile current = queue.poll();
	    	if(dictionary.hasLetter(current.character)) {
				if(current.hasTiles()) {
					dictionary.goToLetter(current.character);
					current.useCharacter();
					moved = true;
				}
				if(current.hasTiles()) queue.add(current);
			} else {
				if(player.hasWild()) {
					dictionary.useRandomLetter();
					queue.add(current);
					moved = true;
				}else {
					rejectedLetters.add(current);
				}
			}
    	}
    }

    public boolean[] backtrackPathFound (ArrayList<Tile> rejectedLetters, char staticCharacter) {
    	boolean usedStatic = false, pathFound = false;
    	if (dictionary.hasLetter(staticCharacter)) {
    		usedStatic = true;
    		pathFound = true;
    		dictionary.goToLetter(staticCharacter);
    	} else {
    		for(Tile t : rejectedLetters) {
    			if(dictionary.hasLetter(t.character)) {
    				pathFound = true;
    				dictionary.goToLetter(t.character);
    				t.useCharacter();
    				break;
    			}
    		}
    	}
    	
    	return new boolean [] {pathFound, usedStatic};
    }

    public boolean backtrack(ArrayList<Tile> rejectedLetters, boolean useStatic, char staticCharacter,
    																		  PriorityQueue<Tile> queue) {
    	boolean fixed = false, preservedStatic = useStatic;
    	do {
	    	char goneBack = dictionary.goBackLetter();
			if ((goneBack == staticCharacter) && !(dictionary.wordContains(staticCharacter))) {
				preservedStatic = false;
			}
			//Go through rejected letters check to see if the removed letter is there
			boolean rejectedNot = true;
			for(Tile t : rejectedLetters) {
				if (t.character == goneBack) {
					t.charCount += 1;
					rejectedNot = false;
					break;
				}
			}
			//If its not there add it because it gave a dead end
			if (rejectedNot) {
				rejectedLetters.add(new Tile(goneBack, Tile.getValue(goneBack)));
			}
			//check to see if an open path was found if it was use it;
			//add the rejected letters back to queue
			boolean[] backtrackResults = backtrackPathFound(rejectedLetters, staticCharacter);
			if(backtrackResults[0]) {
				for(byte i = (byte) (rejectedLetters.size() - 1); i >= 0; i--) {
					queue.add(rejectedLetters.get(i));
					rejectedLetters.remove(i);
				}
				
				preservedStatic = backtrackResults[1];
				fixed = true;
			}
    	 } while (!fixed);
    	
    	return preservedStatic;
    }
    
    public void validWord(byte[] priorityInformation, char priorityDirection, 
    		ArrayList<Character> priorityWord, ArrayList<Character> testWord, char staticCharacter, 
    		PlayerHand player, byte staticUse, boolean useStatic) {
    	if (dictionary.isWord() && useStatic) { 
			testWord = (wordValue(dictionary.getWord()) >= wordValue(testWord))?
																dictionary.getWord(): testWord;
			byte[] rowCol = startingPosition(player, staticCharacter, staticUse);
			
			if(sufficentPoints(testWord, (priorityDirection == 'v')? rowCol[0]:rowCol[1],
											findQudrant(rowCol[0],rowCol[1]), priorityDirection) >= 20) {
				if (!((sufficentPoints(priorityWord, (priorityDirection == 'v')? 
						priorityInformation[0]:priorityInformation[1], priorityInformation[3], 
						priorityDirection) >= 
						sufficentPoints(testWord, (priorityDirection == 'v')? rowCol[0]:rowCol[1],
											findQudrant(rowCol[0],rowCol[1]), priorityDirection)))) {
					//The current priorityWord is less than the new found word
					//If legal replace information
					if(legal((priorityDirection == 'v')? rowCol[0]:rowCol[1], (byte) (testWord.size() - 1)))
					{
						priorityWord = testWord;
						priorityInformation[0] = rowCol[0];
						priorityInformation[1] = rowCol[1];
						priorityInformation[2] = findQudrant(rowCol[0],rowCol[1]);
						priorityInformation[3] = staticUse;
					}
					
				}
											
			}
		}
    }

    public ArrayList<Character> genWord (char staticCharacter, PlayerHand player) {
    	ArrayList<Character> testWord = new ArrayList<>();
    	ArrayList<Character> priorityWord = new ArrayList<>();
    	ArrayList<Tile> rejectedLetters = new ArrayList<>();
		PriorityQueue<Tile> queue = new PriorityQueue<Tile>();
		byte staticUse = 0;
		boolean useStatic = false;
		
		//Saved priority variables
		char priorityDirection = (player.initialWord.direction == 1)? 'v':'h';
		// [row,col,quad,index]
		byte[] priorityInformation = {-1, -1, 0, -1};
		

		for(Tile tile : player.hand.values()) queue.add(tile);
		
			while(!queue.isEmpty() || !useStatic) {
				Tile current = queue.poll();
				if (((!useStatic) && (dictionary.hasLetter(staticCharacter))) &&
						((current == null) || (Tile.getValue(staticCharacter) >= current.compare))) {
					//use board tiles
					staticUse = playStaticCharacter(staticCharacter);
					if (current != null) queue.add(current);
					useStatic = true;
					
				} else if(current != null) { //use hand tiles
					queue.add(current);
					playHandCharacter(queue, player, rejectedLetters);
				} else { //Backtracking
					useStatic = backtrack(rejectedLetters, useStatic, staticCharacter, queue);
				}
	
				//Check valid word , change better word is found
				validWord(priorityInformation, priorityDirection, priorityWord, testWord,
						  								 staticCharacter, player, staticUse, useStatic);
		}

		if(testWord.size() > 2)
			testWord.set(testWord.size() - 1, (char) staticUse);
		if(priorityWord.size() > 2)
			priorityWord.set(priorityWord.size() - 1, (char) priorityInformation[3]);
    	return (priorityWord.size() > 2)? priorityWord : testWord;
    }

    //Returns array [row , column]
    public byte[] startingPosition(PlayerHand player, char staticCharacter, byte staticPosition) {
    	byte startRow = 0, startCol = 0;
    	if(player.initialWord.direction == 1) {
			startCol = (byte) (player.initialWord.startY + player.initialWord.getPosition(staticCharacter));
			startRow = (byte) (player.initialWord.startX - staticPosition);
		} else {
			startRow = (byte) (player.initialWord.startX + player.initialWord.getPosition(staticCharacter));
			startCol = (byte) (player.initialWord.startY - staticPosition);
		}
    	return new byte [] {startRow, startCol};
    }

    public ScrabbleWord priorityWord (PlayerHand player) {
    	ArrayList<Character> createdWord = new ArrayList<>();
    	boolean extended = false;
    	char orientation = 'X', staticCharacter = 'X', finalCharacter = '0';
    	
    	for(byte i = 0; i < player.initialWord.getLength(); i++) {
    		staticCharacter = player.initialWord.word[i];
    		ArrayList<Character> testWord = genWord(player.initialWord.word[i], player);
    		if (wordValue(testWord) > wordValue(createdWord) ) {
    			createdWord = testWord;
    			finalCharacter = staticCharacter;
    		}
    		dictionary.reset();    		
    	}

    	//Remove static char position and reverse it to correct direction
    	char staticPositionC = createdWord.remove(createdWord.size() - 1);
    	byte staticPosition = (byte) staticPositionC;
    	Collections.reverse(createdWord);
    	//-------
    	byte[] rowCol = null;
    	if (extended) {
    		orientation = (player.initialWord.getDirection() == 1)? 'h':'v';
    	} else {
    		orientation = (player.initialWord.getDirection() == 2)? 'h':'v';
    		rowCol = startingPosition(player, finalCharacter, staticPosition);
    	}
    	return new ScrabbleWord(createdWord.stream().map(i -> i.toString()).collect(Collectors.joining()), 
    							rowCol[0], rowCol[1], orientation);
    }

    public ScrabbleWord getScrabbleWord(char[][] board, char[] availableLetters) {
    	BoardWord initalWord = boardSearch(board);
    	
    	HashMap<Character, Tile> hand = new HashMap<>();
    	byte wildCount = generateHand(hand, availableLetters);

    	PlayerHand player = new PlayerHand(initalWord, hand, wildCount);

        return  priorityWord(player);
    }

}

