package graphics;

/**
 * Character arrays that represent a 5x5 Font for convenience. Used by Screen.
 * @author Noah
 *
 */
class Font5x5 {

    public static final int[] _0 = { 0 , '#', '#', '#',  0 ,
                                    '#',  0 ,  0 ,  0 , '#',
                                    '#',  0 ,  0 ,  0 , '#',
                                    '#',  0 ,  0 ,  0 , '#',
                                     0 , '#', '#', '#',  0
    };

    public static final int[] _1 = { 0 ,  0 , '#',  0 ,  0 ,
                                     0 ,  0 , '#',  0 ,  0 ,
                                     0 ,  0 , '#',  0 ,  0 ,
                                     0 ,  0 , '#',  0 ,  0 ,
                                     0 ,  0 , '#',  0 ,  0
    };

    public static final int[] _2 = { 0 , '#', '#', '#',  0 ,
                                    '#',  0 ,  0 ,  0 , '#',
                                     0 , '#', '#', '#',  0 ,
                                    '#',  0 ,  0 ,  0 ,  0 ,
                                    '#', '#', '#', '#', '#',
    };

    public static final int[] _3 = { 0 , '#', '#', '#', 0 ,
                                    '#',  0 ,  0 ,  0 ,'#',
                                     0 ,  0 , '#', '#', 0 ,
                                    '#',  0 ,  0 ,  0 ,'#',
                                     0 , '#', '#', '#',  0
    };

    public static final int[] _4 = { '#',  0 ,  0,  0, '#',
                                     '#',  0 ,  0,  0, '#',
                                     '#', '#', '#','#','#',
                                      0 ,  0 ,  0,  0, '#',
                                      0 ,  0 ,  0,  0, '#',
                                      0 ,  0 ,  0,  0, '#',
    };

    public static final int[] _5 = { '#', '#', '#', '#', '#',
                                     '#',  0 ,  0 ,  0 ,  0 ,
                                     '#', '#', '#', '#',  0 ,
                                      0 ,  0 ,  0 ,  0 , '#',
                                     '#', '#', '#', '#',  0
    };

    public static final int[] _6 = { 0 , '#', '#', '#',  0 ,
                                    '#',  0 ,  0 ,  0 ,  0 ,
                                    '#', '#', '#', '#',  0 ,
                                    '#',  0 ,  0 ,  0 , '#',
                                     0 , '#', '#', '#',  0

    };

    public static final int[] _7 = { '#', '#', '#', '#','#',
                                      0 ,  0 ,  0 ,  0 ,'#',
                                      0 ,  0 ,  0 , '#', 0 ,
                                      0 ,  0 , '#',  0 , 0 ,
                                      0 ,  0 , '#',  0 , 0
    };

    public static final int[] _8 = {  0 , '#', '#', '#', 0 ,
                                     '#',  0 ,  0 ,  0 ,'#',
                                      0 , '#', '#', '#', 0 ,
                                     '#',  0 ,  0 ,  0 ,'#',
                                      0 , '#', '#', '#', 0
    };

    public static final int[] _9 = {  0 , '#', '#', '#',  0 ,
                                     '#',  0 ,  0 ,  0 , '#',
                                     '#', '#', '#', '#', '#',
                                      0 ,  0 ,  0 ,  0 , '#',
                                     '#', '#', '#', '#',  0

    };

    public static final int[] SPACE = { 0 ,  0 ,  0 ,  0 , 0 ,  0 ,
                                        0 ,  0 ,  0 ,  0 , 0 ,  0 ,
                                        0 ,  0 ,  0 ,  0 , 0 ,  0 ,
                                        0 ,  0 ,  0 ,  0 , 0 ,  0 ,
                                        0 ,  0 ,  0 ,  0 , 0 ,  0 ,
                                        0 ,  0 ,  0 ,  0 , 0 ,  0

    };

    static int[] getChar(char c){

        c = Character.toUpperCase(c);

        switch(c){
            case '0':
                return Font5x5._0;
            case '1':
                return Font5x5._1;
            case '2':
                return Font5x5._2;
            case '3':
                return Font5x5._3;
            case '4':
                return Font5x5._4;
            case '5':
                return Font5x5._5;
            case '6':
                return Font5x5._6;
            case '7':
                return Font5x5._7;
            case '8':
                return Font5x5._8;
            case '9':
                return Font5x5._9;
        }
        return Font5x5.SPACE;
    }
}

