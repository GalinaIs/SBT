public class Main {
    public static void main(String[] args) {
        WatchDirectoryService watchDirectory =
                new WatchDirectoryService("D:\\JavaSBTGit\\SBT\\Ex1\\src\\main\\resources\\monitor");
        Thread thread = new Thread(watchDirectory);
        thread.start();
    }
}
