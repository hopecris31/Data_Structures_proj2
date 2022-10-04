package proj2;

/**
 * CS 151 Project 2
 * Oct 4, 2022
 *
 *  A class that represents a sequence ADT.
 *
 *  INVARIANTS:
 *  holds items of the same type
 *
 *  If size>0:
 *      The contents are stored in sequence at indexes 0 through size-1
 *      If there's no current index, current = -1
 *      the contents at Indexes >= items are irrelevant
 *      if size - 0, the items are irrelevant
 *  if 0 <= size <= holder.length
 *      currentIndex can never be greater than size
 *      Items can be accessed via the "current" marker
 *
 *
 *
 *  INSTANCE VARIABLES:
 *      holder -- the String array that holds the items
 *      items -- the number of elements currently in the Sequence
 *      currentIndex -- the current index of the holder
 */

public class Sequence {
    private final int DEFAULT_SIZE = 10;
    private String[] holder;
    private int items; //the number of items in the holder
    private int currentIndex;
    private final int NO_INDEX = -1;

    /**
     * Creates a new sequence with initial capacity 10.
     */
    public Sequence() {
         this.holder = new String[DEFAULT_SIZE];
         this.currentIndex = NO_INDEX;
         this.items = 0;
    }

    //make new array, copy all elements to array, then set this.holder = new array

    /**
     * Creates a new sequence. (non-default constructor)
     *
     * @param initialCapacity the initial capacity of the sequence.
     */
    public Sequence(int initialCapacity){
        this.holder = new String[initialCapacity];
        this.currentIndex = NO_INDEX;
        this.items = 0;
    }


    /**
     * Adds a string to the sequence in the location before the
     * current element. If the sequence has no current element, the
     * string is added to the beginning of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addBefore(String value) {
        if(this.items == this.getCapacity()){ //if at max capacity
            doubleCapacity(); //doubles capacity
            this.shiftIncludingCurrent(value); //shifts elements at current and after and inserts the new value
        }
        else if(!this.isCurrent()){ //if currentIndex == -1
            this.currentIndex = 0; //the current index becomes 0
            this.setIndexValue(this.currentIndex, value); //the value is set to the currentIndex
        }
        else{ //default
            this.shiftIncludingCurrent(value);
        }
        this.items++;
    }

    /**
     * increases the capacity of a sequence by expanding to twice its current capacity plus 1.
     */
    private void doubleCapacity(){
        int doubleCapacity = this.getCapacity()*2;
        this.ensureCapacity(doubleCapacity+1);
    }

    /**
     * shifts all elements from currentIndex and after one to the right (addBefore)
     * adds a value in the holder in the spot of currentIndex, before previous currentIndex
     * @param value a value to be added
     */
    private void shiftIncludingCurrent(String value){
        for(int i = this.size()-1; i >= this.currentIndex; i--){
            this.holder[i+1] = this.holder[i]; //shifting elements to the right 1
        }
        this.setIndexValue(this.currentIndex, value);
    }


    /**
     * Adds a string to the sequence in the location after the current
     * element. If the sequence has no current element, the string is
     * added to the end of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addAfter(String value) { // should I make this into a switch case
        if(this.endOfSequenceReached()){
            doubleCapacity();
            this.shiftExcludingCurrent(value);
            this.items++;
        }
        else if(!this.isCurrent()){ //if there is no current index
            this.setIndexValue(this.getLastIndex(), value); //set last index to value
            this.setCurrentIndex(getLastIndex());
            this.items++;
        }
        else{
            this.shiftExcludingCurrent(value);
            this.items++;
        }
    }

    /**
     * shifts all elements after currentIndex over one to the right
     * adds a value in the holder after currentIndex
     * sets currentIndex to the value just added
     * @param value a value to be added
     */
    private void shiftExcludingCurrent(String value){
        for(int i = this.size()-1; i > this.currentIndex; i--){
            this.holder[i+1] = this.holder[i]; //shifting elements to the right 1
        }
        this.advance();
        this.setIndexValue(this.currentIndex, value); //setting the current index to value to be added
    }

    /**
     * sets the current index to the index specified
     * @param newIndex a new current index
     */
    private void setCurrentIndex (int newIndex){ //this was private, but can I make this public to use in unit tesing
        this.currentIndex = newIndex;
    }

    /**
     *Sets a value to a specified index
     * @param index index to be set
     * @param value value to take place of the index
     */
    private void setIndexValue(int index, String value){
        this.holder[index] = value;
    }

    /**
     * sets the number of items in the sequence to 0
     */
    private void clearItems(){
        this.items = 0;
    }

    /**
     * @return true if and only if the sequence has a current element.
     */
    public boolean isCurrent() {
        return this.currentIndex != NO_INDEX; //double check this
    }


    /**
     * @return the capacity of the sequence.
     */
    public int getCapacity() {
        return this.holder.length;
    }

    /**
     * gets the last index in a seqence
     * @return the last index of a sequence
     */
    private int getLastIndex(){
        return getCapacity()-1;
    }

    /**
     *
     * @return True if the end of the sequence has been reached, False if not
     */
    private boolean endOfSequenceReached(){
        return this.currentIndex == getLastIndex();
    }

    //switch only works if you looking to switch one possible variable, used to check multiple different values (one variable, then check at least two different values a variable can take)

    /**
     * @return the element at the current location in the sequence, or
     * null if there is no current element.
     */
    public String getCurrent() {
        if (isCurrent()){
            return this.holder[this.currentIndex]; //is this an appropriate helper method
        }
        else{
            return null;
        }
    }

    /**
     * gets the currrent index of the Sequence
     * @return the current index
     */
    public int getCurrentIndex(){
        return this.currentIndex;
    }

    /**
     * Increase the sequence's capacity to be
     * at least minCapacity.  Does nothing
     * if current capacity is already >= minCapacity.
     *
     * @param minCapacity the minimum capacity that the sequence
     * should now have.
     */
    public void ensureCapacity(int minCapacity) {
        //make new array, copy all elements to array, then set this.holder = new array
        if(this.getCapacity() < minCapacity){
            String[] newHolder = new String[minCapacity];
            for(int i = 0; i < this.getCapacity(); i++){ //check the i< condition is correct
                newHolder[i] = this.holder[i]; // use helper method to set index??
            }
            this.holder = newHolder;
        }
    }


    /**
     * Places the contents of another sequence at the end of this sequence.
     *
     * If adding all elements of the other sequence would exceed the
     * capacity of this sequence, the capacity is changed to make room for
     * all of the elements to be added.
     *
     * Postcondition: NO SIDE EFFECTS!  the other sequence should be left
     * unchanged.  The current element of both sequences should remain
     * where they are. (When this method ends, the current element
     * should refer to the same element that it did at the time this method
     * started.)
     *
     * @param another the sequence whose contents should be added.
     */
    public void addAll(Sequence another) {
        if(another.items <= this.remainingCapacity()){
            this.addItems(another);
            }
        else if(another.items > this.remainingCapacity()){
            int minCapacity = this.size() + another.size();
            this.ensureCapacity(minCapacity);
            this.addItems(another);
        }
    }

    /**
     * adds items from another Sequence to this sequence.
     * @param another the other sequence to be added to original
     */
    private void addItems(Sequence another){
        for(int i = 0; i < another.items; i++) { //for all items that are to be added
            this.addAfter(another.holder[i]);  //double check implementation of this helper
            this.items ++;  //implement this line in addbefore and addafter
        }
    }

    /**
     * gets the remaining capacity in a Sequence.
     * @return the number of remaining spaces in the Sequence.
     */
    private int remainingCapacity(){
        return this.getCapacity() - this.items;
    }


    /**
     * Move forward in the sequence so that the current element is now
     * the next element in the sequence.
     *
     * If the current element was already the end of the sequence,
     * then advancing causes there to be no current element.
     *
     * If there is no current element to begin with, do nothing.
     */
    public void advance() {
        if(isCurrent()) {
            if (this.currentIndex == this.size()-1) { // if the current index is at the end of the sequence
                this.currentIndex = NO_INDEX;  //is this.getCapacity()-1 the best way to express "at the last index"
            }
            this.currentIndex += 1;
        }
    }


    /**
     * Make a copy of this sequence.  Subsequence changes to the copy
     * do not affect the current sequence, and vice versa.
     *
     * Postcondition: NO SIDE EFFECTS!  This sequence's current
     * element should remain unchanged.  The clone's current
     * element will correspond to the same place as in the original.
     *
     * @return the copy of this sequence.
     */
    public Sequence clone() {
        Sequence newSequence = new Sequence(this.getCapacity());
        newSequence.currentIndex = this.currentIndex;  //double check these, pointers may be invalid
        newSequence.items = this.items; //I can set this directly equal to the number right
        for (int i = 0; i < this.getCapacity(); i++){
            newSequence.holder[i] = this.holder[i];
        }
        return newSequence;
    }


    /**
     * Remove the current element from this sequence.  The following
     * element, if there was one, becomes the current element.  If
     * there was no following element (current was at the end of the
     * sequence), the sequence now has no current element.
     *
     * If there is no current element, does nothing.
     */
    public void removeCurrent() {
        if(this.isCurrent()){ //as remove() shifts elements down, the next element automatically becomes current without having to set it
            this.remove();
            if(this.endOfSequenceReached()){
                this.setCurrentIndex(NO_INDEX);
            }
            this.items--;
        }
    }



    /**
     * removes the current element in a sequence by shifting all elements from the current index to the left
     */
    private void remove(){
        for(int i = this.currentIndex; i < this.size(); i++){
            this.holder[i] = this.holder[i+1];
        }
    }


    /**
     * @return the number of elements stored in the sequence.
     */
    public int size() {
        return this.items;
    }


    /**
     * Sets the current element to the start of the sequence.  If the
     * sequence is empty, the sequence has no current element.
     */
    public void start() {
        if(this.isEmpty()){
            this.setCurrentIndex(NO_INDEX);
        }
        else{
            this.advance();
        }
    }


    /**
     * Reduce the current capacity to its actual size, so that it has
     * capacity to store only the elements currently stored.
     */
    public void trimToSize() {
        String[] newHolder = new String[this.size()];
        for(int i = 0; i < this.size(); i++){
            newHolder[i] = this.holder[i];
        }
        this.holder = newHolder;
    }


    /**
     * Produce a string representation of this sequence.  The current
     * location is indicated by a >.  For example, a sequence with "A"
     * followed by "B", where "B" is the current element, and the
     * capacity is 5, would print as:
     *
     *    {A, >B} (capacity = 5)
     *
     * The string you create should be formatted like the above example,
     * with a comma following each element, no comma following the
     * last element, and all on a single line.  An empty sequence
     * should give back "{}" followed by its capacity.
     *
     * @return a string representation of this sequence.
     */
    public String toString() {
        String sequenceString = "{";

        if(this.isEmpty()){
            sequenceString += "}";
        }
        for(int i = 0; i < this.size(); i++){
            String current = this.holder[i];
            if(i == this.currentIndex){
                current = ">" + current;
            }
            if(i != this.getLastIndex()){
                current += ",";
            }
            sequenceString += current;
        }
        sequenceString += " (capacity = " + this.getCapacity() + ")";
        return sequenceString;
    }

    /**
     * Checks whether another sequence is equal to this one.  To be
     * considered equal, the other sequence must have the same size
     * as this sequence, have the same elements, in the same
     * order, and with the same element marked
     * current.  The capacity can differ.
     *
     * Postcondition: NO SIDE EFFECTS!  this sequence and the
     * other sequence should remain unchanged, including the
     * current element.
     *
     * @param other the other Sequence with which to compare
     * @return true iff the other sequence is equal to this one.
     */
    public boolean equals(Sequence other) {
        if(this.size() != other.size()) {
            return false;
        }
        if (this.currentIndex != other.currentIndex){
            return false;
        }
        for(int i=0; i<this.size(); i++){
            if(!this.holder[i].equals(other.holder[i])){
                return false;
            }
        }
        return true;
    }


    /**
     *
     * @return true if Sequence empty, else false
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     *  empty the sequence.  There should be no current element.
     */
    public void clear() {
        for(int i=0; i < this.size(); i++){
            this.holder[i] = null; //check this
        }
        this.clearItems();
        this.currentIndex = NO_INDEX;
    }

}
