# CustomEnchantments
Plugin to manage custom enchantments and allow them to be easily added to items.

Please note that this plugin only manages, contains methods to create custom enchantments easily and does not contain any custom enchantments inside.

#### API Usage
See [CustomEnchantmentsAPI](https://github.com/Naaathan/CustomEnchantments/tree/master/src/main/java/net/kyuzi/customenchantments/CustomEnchantmentsAPI.java) for all methods relating to custom enchantments

See [ExamplePlugin](https://github.com/Naaathan/CustomEnchantments/tree/master/src/test/java/ExamplePlugin.java)for an example of using [CustomEnchantmentsPlugin](https://github.com/Naaathan/CustomEnchantments/tree/master/net/kyuzi/customenchantments/plugin/CustomEnchantmentsPlugin.java)

See [ExampleEnchantment] (https://github.com/Naaathan/CustomEnchantments/tree/master/src/test/java/ExampleEnchantment.java) for an example of using [CustomEnchantment](https://github.com/Naaathan/CustomEnchantments/tree/master/net/kyuzi/customenchantments/enchantment/CustomEnchantment.java)


#### Commands

`/customenchantments - Display help message.`

`/customenchantments list - Lists all custom enchantments currently loaded.`

`/customenchantments disable <enchantment> - Temporarily disables the custom enchantment named <enchantment>.`

`/customenchantments enable <enchantment> - Temporarily enables the custom enchantment named <enchantment>.`

`/customenchantments remove <enchantment> - Removes the custom enchantment named <enchantment> from the item that you are holding.`

`/customenchantments add <enchantment> <level> - Adds the custom enchantment named <enchantment> with level <level> to the item that you are holding.`

#### Permissions

All permissions associated with commands are `customenchantments` followed by the subcommand name (apart from the help message which is `customenchantments.help`). E.g. `/customenchantments list`'s permission node is `customenchantments.list`.
