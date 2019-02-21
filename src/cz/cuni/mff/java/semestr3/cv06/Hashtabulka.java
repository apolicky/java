package cz.cuni.mff.java.semestr3.cv06;

public class Hashtabulka {
    //nemam paru, jak se delaji hashovaci tabulky. Budu opisovat :D
    private class Entry{
        String key;
        Object value;

        public Entry(String key, Object value){
            this.key=key;
            this.value=value;
        }
    }

    private Entry[] entries;
    private int size;
    private static final int DEFAULT_SIZE = 10;
    private static final double MAX_FACTOR = 0.75;

    public Hashtabulka(){
        entries=new Entry[DEFAULT_SIZE];
    }

    public Object get(String key){
        int hashCode = key.hashCode();
        int index=hashCode%entries.length;

        while(entries[index] !=null && !entries[index].key.equals(key)){
            index=(index+1)% entries.length;
        }
        if(entries[index]==null){
            return null;
        }
        else{
            return entries[index].value;
        }
    }

    public void put(String key,Object value){
        int hashCode = key.hashCode();
        int index=hashCode%entries.length;

        while(entries[index] !=null && !entries[index].key.equals(key)){
            index=(index+1)% entries.length;
        }
        if(entries[index]==null){
            size++;
            if((size/ (double)entries.length)>MAX_FACTOR){
                Entry[] old_entries = entries;
                entries = new Entry[old_entries.length+DEFAULT_SIZE];
                for (Entry entry : old_entries){
                    if(entry!= null){
                        put(entry);
                    }
                }
                put(new Entry(key,value));
                return;
            }
        }
        entries[index]=new Entry(key,value);
    }

    private void put(Entry entry){
        int hashCode = entry.key.hashCode();
        int index= hashCode%entries.length;

        while(entries[index] !=null && !entries[index].key.equals(entry.key)){
            index=(index+1)% entries.length;
        }
        entries[index]=entry;
    }

    public void forEachValue(Operace operace){
        for(Entry entry:entries){
            if(entry!=null){
                operace.udelej(entry.value);
            }
        }

    }
    public static void main(String[] args){
        Hashtabulka hT=new Hashtabulka();

        //No proste to bude fungovat
        // hT.put();

        hT.forEachValue(o -> System.out.println(o));
    }
}

