package utils;

public class Console {
	public static void log(String s, Object... list) {
        String out = new String(s), temp;
        
        for(Object o: list) {
            temp = String.valueOf(o);
            out = out.replaceFirst("%s", temp);
        }
        
        System.err.println(out);
    }
    
    public static void log(Object... list) {
        String out = "";
        int i;
        for (i = 0; i < list.length - 1; i++) {
            out += String.valueOf(list[i]) + ", ";
        }
        out += String.valueOf(list[i]);
        
        System.err.println(out);
    }

    public static void dir(Object input) {
        System.err.println(Console.dir(input, "", " "));
    }

    public static String dir_process_int(int[] subArray, String nextOffset, String offsetStep){
        String out = "";
        for (int i = 0; i < subArray.length; i++) {
            out += Console.dir(subArray[i], nextOffset, offsetStep) + (i < subArray.length - 1 ? "," : "") + "\n";    
        }
        
        return out;
    }

    public static String dir(Object input, String offset, String offsetStep) {
        if (input == null) {
            return offset + String.valueOf(input);
        }
        
        Class<?> classInstance = input.getClass();
        
        if (classInstance.isArray()) {
            String nextOffset = offset + offsetStep;
            String out = offset + "[\n";
            
            if (classInstance.equals(int[].class)) { // For each primitive type
                int[] subArray = (int[]) input;
                out += Console.dir_process_int(subArray, nextOffset, offsetStep);
            } else { // For non primitive types
                Object[] subArray = (Object[]) input;
                for (int i = 0; i < subArray.length; i++) {
                    out += Console.dir(subArray[i], nextOffset, offsetStep) + (i < subArray.length - 1 ? "," : "") + "\n";    
                } 
            }
            
            out += offset + "]";
            return out;
        } else if (classInstance.equals(String.class)) {
            return offset + "\"" + String.valueOf(input) + "\"";
        } else {
            return offset + String.valueOf(input);
        }
    }
}
