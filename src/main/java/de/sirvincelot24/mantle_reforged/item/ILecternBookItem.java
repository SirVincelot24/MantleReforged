package de.sirvincelot24.mantle_reforged.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import de.sirvincelot24.mantle_reforged.network.MantleNetwork;
import de.sirvincelot24.mantle_reforged.network.packet.OpenLecternBookPacket;

/** Interface for book items to work with lecterns */
public interface ILecternBookItem {
  /**
   * Called serverside to open the lectern screen when a lectern is clicked
   * @param world    World
   * @param pos      Block position
   * @param player   Player instance
   * @param book     Book stack
   * @return  True if the normal screen should not be opened
   */
  default boolean openLecternScreen(Level world, BlockPos pos, Player player, ItemStack book) {
    MantleNetwork.INSTANCE.sendTo(new OpenLecternBookPacket(pos, book), player);
    return true;
  }

  /**
   * Called client side to open the lectern screen, unsafe to call serverside.
   * Typical implementions will make use of {@link de.sirvincelot24.mantle_reforged.client.book.data.BookData#openGui(BlockPos, ItemStack)}
   * @param pos   Lectern position
   * @param book  Book stack instance
   */
  void openLecternScreenClient(BlockPos pos, ItemStack book);
}
