package dev.anvilcraft.resource.ageratum.client.gui;

import net.minecraft.resources.Identifier;

/** 验证不同方块的自定义预览选择，以及重复注册不会覆盖原提供器。 */
public final class StructureGuiProvidersTest {
    private StructureGuiProvidersTest() {
    }

    public static void verify() {
        Identifier first = Identifier.fromNamespaceAndPath("test", "preview_first");
        Identifier second = Identifier.fromNamespaceAndPath("test", "preview_second");
        StructureGuiProviders.Provider provider = context -> null;
        if (StructureGuiProviders.get(first) != null) {
            throw new AssertionError("未注册方块应使用标准菜单路径");
        }
        StructureGuiProviders.register(first, provider);
        if (StructureGuiProviders.get(first) != provider || StructureGuiProviders.get(second) != null) {
            throw new AssertionError("提供器应只匹配其注册的方块");
        }
        boolean rejected = false;
        try {
            StructureGuiProviders.register(first, context -> null);
        } catch (IllegalStateException exception) {
            rejected = true;
        }
        if (!rejected || StructureGuiProviders.get(first) != provider) {
            throw new AssertionError("重复注册必须保留原提供器并明确报错");
        }
    }
}
