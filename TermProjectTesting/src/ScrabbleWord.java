/**
  *<p><b>You may add additional methods, but may not change existing methods</b></p>
  *
  * The ScrabbleWord class has four components:
  *
  *<ol>
  *  <li>the word (String)--UPPERCASE letters, underscore '_' represents blank (wildcard)</li>
  *  <li>starting row and column (int)</li>
  *  <li>orientation:  v (vertical) or h (horizontal)  (char)
  *</ol>
  *
  *@author Philip Chan
  */

public class ScrabbleWord
{
    private String  word;  // word
    private int     startRow, startCol;   // starting row and column of the word
    private char    orientation;  // v for vertical, h for horizontal

    /** 
     * default constructor with empty string at (0,0) horizontally
     */
    public ScrabbleWord()
    {
	word = "";
	startRow = 0;
	startCol = 0;
	orientation = 'h';
    }

    /** 
     * constructor with user supplied values
     * @param myWord the corresponding word
     * @param myStartRow starting row
     * @param myStartCol starting column
     * @param myOrientation orientation of the word: v or h
     */
    public ScrabbleWord(String myWord, int myStartRow, int myStartCol, char myOrientation)
    {
	word = myWord;
	startRow = myStartRow;
	startCol = myStartCol;
	orientation = myOrientation;
    }


    /**
     * return the word
     * @return the corresponding word
     */
    public String getScrabbleWord()
    {
	return word;
    }

    /**
     * get the starting row
     * @return the starting row
     */
    public int getStartRow()
    {
	return startRow;
    }


    /**
     * get the starting column
     * @return the starting column
     */
    public int getStartColumn()
    {
	return startCol;
    }


    /**
     * get the orientation
     * @return the orientation
     */
    public int getOrientation()
    {
	return orientation;
    }
}
