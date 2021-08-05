package cc.sfclub.polar.loader;

import asia.kala.ansi.AnsiString;
import cc.sfclub.polar.Bot;
import cc.sfclub.polar.EventChannel;
import cc.sfclub.polar.platfrom.IPlatform;
import cc.sfclub.polar.platfrom.IPlatformBot;
import cc.sfclub.polar.platfrom.PlatformManager;
import cc.sfclub.polar.user.User;
import cc.sfclub.polar.user.UserManager;
import cc.sfclub.polar.user.data.UserData;
import cc.sfclub.polar.util.ChatColor;
import io.ebean.Database;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.jar.Manifest;

@Slf4j
public class Loader extends Bot {
    public Loader(UserManager userManager, PlatformManager platformManager, Database database, EventChannel eventChannel) {
        super(userManager, platformManager, database, eventChannel);
    }
    private static Manifest manifest;

    private static final void init(){
        try {
            manifest=new Manifest(Loader.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(manifest==null || manifest.getMainAttributes().getValue("Commit-Hash") == null){
            manifest = new Manifest();
            manifest.getMainAttributes().putValue("Build-Date","Invalid");
            manifest.getMainAttributes().putValue("Build-By","Invalid");
            manifest.getMainAttributes().putValue("Commit-Hash","Invalid");
            manifest.getMainAttributes().putValue("Version","Invalid");
        }
    }
    public static void main(String[] args) {
        init();
        log.info(ChatColor.of("&bPolarCore build &a"+manifest.getMainAttributes().getValue("Commit-Hash")+" &bver &a"+manifest.getMainAttributes().getValue("Version")));
    }
}
