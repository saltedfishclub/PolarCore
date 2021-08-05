package cc.sfclub.polar.loader;

import cc.sfclub.polar.Bot;
import cc.sfclub.polar.EventChannel;
import cc.sfclub.polar.platfrom.PlatformManager;
import cc.sfclub.polar.user.UserManager;
import cc.sfclub.polar.util.ChatColor;
import io.ebean.Database;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.jar.Manifest;

@Slf4j
public class Loader extends Bot {
    public Loader(UserManager userManager, PlatformManager platformManager, Database database, EventChannel eventChannel) {
        super(userManager, platformManager, database, eventChannel);
    }
    private static Manifest manifest;
    static {
        try {
            manifest = new Manifest(Loader.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (manifest == null || manifest.getMainAttributes().getValue("Commit-Hash") == null) {
            manifest = new Manifest();
            manifest.getMainAttributes().putValue("Build-Date", "Invalid");
            manifest.getMainAttributes().putValue("Build-By", "Invalid");
            manifest.getMainAttributes().putValue("Commit-Hash", "Invalid");
            manifest.getMainAttributes().putValue("Version","Invalid");
        }
    }
    public static void main(String[] args) {
        log.info(ChatColor.of("&bPolarCore initializing..."));
        String buildVer = manifest.getMainAttributes().getValue("Version");
        String buildCommit = manifest.getMainAttributes().getValue("Commit-Hash");
        String buildDate = manifest.getMainAttributes().getValue("Build-Date");
        String buildBy = manifest.getMainAttributes().getValue("Build-By");
        boolean thirdparty = !buildBy.equals("runner");
        log.info(ChatColor.of("&bBuild &a" + buildVer + "-" + buildCommit + " &bDate: &a" + buildDate + " " + (thirdparty ? "&cTHIRD PARTY BUILD BY " + buildBy : "&bCI BUILD")));
    }
}
