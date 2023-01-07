package org.springframework.samples.tyrantsOfTheUnderdark.checkers;

public class Preconditions {
    Boolean condition = true;
    public Preconditions(Boolean condition) {
        this.condition=condition;
    }
    public static Preconditions of(Boolean condition){
        return new Preconditions(condition);
    }
    public static Preconditions check(Boolean b){
        return Preconditions.of(b);
    }
    /**
     * throws exception based on a formated mesagge (internal String.format)
     * @param format
     * @param args
     * @throws Exception
     */
    public void formattedError(String format, Object... args) throws Exception{
        if(!this.condition) throw new Exception(String.format(format,args));
    }
  /**
     * thow exception whith message
     * @param format
     * @param args
     * @throws Exception
     */
    public void error(String message) throws Exception{
        if(!this.condition) throw new Exception(message);
    }
}
