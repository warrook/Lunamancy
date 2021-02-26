package warrook.lunamancy.utils.network;

import org.jetbrains.annotations.NotNull;
import warrook.lunamancy.utils.enums.Moonlight;

public interface LightContainer {
    Moonlight getLightType();
    float getAmount();
    float getCapacity();
}
