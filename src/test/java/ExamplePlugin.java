import net.kyuzi.customenchantments.CustomEnchantmentsAPI;
import net.kyuzi.customenchantments.plugin.CustomEnchantmentsPlugin;

/**
 * ExamplePlugin shows an example of how to use a custom enchantments plugin.
 *
 * @see net.kyuzi.customenchantments.plugin.CustomEnchantmentsPlugin
 */
public class ExamplePlugin extends CustomEnchantmentsPlugin {

    @Override
    public void onDisable() {
        unregisterEnchantments(CustomEnchantmentsAPI.getEnchantmentByName("Example"));
    }

    @Override
    public void onEnable() {
        registerEnchantments(new ExampleEnchantment());
    }

}
