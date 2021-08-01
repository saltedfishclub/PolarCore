package cc.sfclub.polar.platfrom;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

/**
 * 代表一个平台。
 */
public interface IPlatform {

    String getName();
    List<IBot> getBots();

    /**
     * 由 Core 定时调用刷新逻辑。
     * 自行保证线程安全问题
     */
    void refreshData();
    //todo getProviderPlugin
}
