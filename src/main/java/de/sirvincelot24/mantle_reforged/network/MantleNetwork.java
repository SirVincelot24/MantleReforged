package de.sirvincelot24.mantle_reforged.network;

import net.minecraftforge.network.NetworkDirection;
import de.sirvincelot24.mantle_reforged.Mantle;
import de.sirvincelot24.mantle_reforged.fluid.transfer.FluidContainerTransferPacket;
import de.sirvincelot24.mantle_reforged.network.packet.DropLecternBookPacket;
import de.sirvincelot24.mantle_reforged.network.packet.OpenLecternBookPacket;
import de.sirvincelot24.mantle_reforged.network.packet.OpenNamedBookPacket;
import de.sirvincelot24.mantle_reforged.network.packet.SwingArmPacket;
import de.sirvincelot24.mantle_reforged.network.packet.UpdateHeldPagePacket;
import de.sirvincelot24.mantle_reforged.network.packet.UpdateLecternPagePacket;

public class MantleNetwork {
  /** Network instance */
  public static final NetworkWrapper INSTANCE = new NetworkWrapper(Mantle.getResource("network"));

  /**
   * Registers packets into this network
   */
  public static void registerPackets() {
    INSTANCE.registerPacket(OpenLecternBookPacket.class, OpenLecternBookPacket::new, NetworkDirection.PLAY_TO_CLIENT);
    INSTANCE.registerPacket(UpdateHeldPagePacket.class, UpdateHeldPagePacket::new, NetworkDirection.PLAY_TO_SERVER);
    INSTANCE.registerPacket(UpdateLecternPagePacket.class, UpdateLecternPagePacket::new, NetworkDirection.PLAY_TO_SERVER);
    INSTANCE.registerPacket(DropLecternBookPacket.class, DropLecternBookPacket::new, NetworkDirection.PLAY_TO_SERVER);
    INSTANCE.registerPacket(SwingArmPacket.class, SwingArmPacket::new, NetworkDirection.PLAY_TO_CLIENT);
    INSTANCE.registerPacket(OpenNamedBookPacket.class, OpenNamedBookPacket::new, NetworkDirection.PLAY_TO_CLIENT);
    INSTANCE.registerPacket(FluidContainerTransferPacket.class, FluidContainerTransferPacket::new, NetworkDirection.PLAY_TO_CLIENT);
  }
}
