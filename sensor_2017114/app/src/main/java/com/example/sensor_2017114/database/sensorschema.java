package com.example.sensor_2017114.database;

public class sensorschema {
    public static final class Accelerometertable{
        public static final String NAME="accelerometer";
        public static final class Cols{
            public static final String timestamp="timestamp";
            public static final String X="X";
            public static final String Y="Y";
            public static final String Z="Z";


        }
    }
    public static final class GPStable{
        public static final String NAME="gps";
        public static final class Cols{
            public static final String timestamp="timestamp";
            public static final String latitude="latitude";
            public static final String longitude="longitude";


        }
    }
    public static final class Wifitable{
        public static final String NAME="Wifi";
        public static final class Cols{
            public static final String timestamp="timestamp";
            public static final String Strength="Strength";
            public static final String Accesspoint="Accesspoint";



        }
    }
    public static final class Mictable{
        public static final String NAME="Microphone";
        public static final class Cols{
            public static final String timestamp="timestamp";
            public static final String filename="filename";



        }
    }
}
