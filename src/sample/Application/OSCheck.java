package sample.Application;

public class OSCheck {

    private static String OS = null;
    private static String getOS()
    {
        if(OS == null) { OS = System.getProperty("os.name"); }
        return OS;
    }
    private static boolean isWindows()
    {
        return getOS().startsWith("Windows");
    }

    private static boolean isLinux()
    {
        return getOS().startsWith("Linux");
    }

    private static boolean isOSX()
    {
        return getOS().startsWith("OSX");
    }

    protected static void updateIPFS()
    {
        if (isWindows()){
            System.out.println("Detected Windows OS - Installing IPFS for Windows");
        }

        else if (isLinux())
        {
            System.out.println("Detected Linux OS - Installing IPFS for Linux");
        }

        else if (isOSX())
        {
            System.out.println("Detected OSX OS - Installing IPFS for OSX");
        }
        System.out.println("OS Version : " + getOS());
    }


}
