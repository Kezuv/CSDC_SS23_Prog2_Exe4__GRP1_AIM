package at.ac.fhcampuswien.fhmdb.patterns;

public class SortingState {
    private SortState state = new NoneSortState(); // can be any interface implemented class

    public void setState(SortState state) {
        this.state = state;
    }

    public SortState getState() {
        return state;
    }

    public void next(){
        state.next(this);
    }


    public String displayText(){
        return state.displayText();
    }

}

