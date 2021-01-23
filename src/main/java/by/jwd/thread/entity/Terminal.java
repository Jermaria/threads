package by.jwd.thread.entity;

public class Terminal {
    
    private int terminalNumber;
    private boolean isOccupied;
    
    public Terminal() {}
    public Terminal(int terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public int getTerminalNumber() {
        return terminalNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isOccupied ? 1231 : 1237);
        result = prime * result + terminalNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Terminal other = (Terminal) obj;
        if (isOccupied != other.isOccupied)
            return false;
        if (terminalNumber != other.terminalNumber)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Terminal [terminalNumber=");
        builder.append(terminalNumber);
        builder.append(", isOccupied=");
        builder.append(isOccupied);
        builder.append("]");
        return builder.toString();
    }

}
